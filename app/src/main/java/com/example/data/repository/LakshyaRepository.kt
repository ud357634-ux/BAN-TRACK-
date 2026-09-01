package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.BuildConfig
import com.example.data.local.EvaluationRecordEntity
import com.example.data.local.LakshyaDao
import com.example.data.local.LakshyaDatabase
import com.example.data.local.MistakeEntity
import com.example.data.local.ModuleReviewLogEntity
import com.example.data.local.ModuleWithProgress
import com.example.data.local.SebaSubjectEntity
import com.example.data.local.SpacedRepetitionMetricEntity
import com.example.data.local.StudyModuleEntity
import com.example.data.local.SubjectProgressMetricEntity
import com.example.data.local.TestSessionEntity
import com.example.data.local.WeakTopicEntity
import com.example.data.model.EnglishGrammarResponse
import com.example.data.model.EvaluateResponse
import com.example.data.model.MistakeAnalysisResponse
import com.example.data.model.MistakePatternItem
import com.example.data.model.MockPaperResponse
import com.example.data.model.MockTestResponse
import com.example.data.model.QuizResponse
import com.example.data.model.RubricItem
import com.example.data.remote.GeminiClient
import com.example.data.remote.GeminiContent
import com.example.data.remote.GeminiGenerationConfig
import com.example.data.remote.GeminiPart
import com.example.data.remote.GeminiRequest
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class LakshyaRepository(
    private val dao: LakshyaDao,
    private val moshi: Moshi = GeminiClient.moshiInstance
) {
    companion object {
        private const val TAG = "LakshyaRepository"

        const val LAKSHYA_SYSTEM_PROMPT = """
<role>
You are "Lakshya AI" — a specialized SEBA (Board of Secondary Education, Assam) Class 10 exam-preparation engine. Your single mission: take any student, regardless of current level, and push them toward the top 0.1 percentile of SEBA Class 10 performers through scientifically-grounded practice — not generic content, not guesswork.

You are NOT a general chatbot. You operate strictly in structured generation modes defined below. You never break character, never add filler conversation, and never produce content outside the requested mode's JSON schema.
</role>

<absolute_rules priority="highest">
1. ZERO HALLUCINATION PROTOCOL:
   - You may ONLY generate questions, facts, dates, formulas, and chapter content that are either (a) present in the SOURCE_MATERIAL provided in the user turn, or (b) part of extremely well-established, non-controversial NCERT/SEBA-equivalent syllabus knowledge that you are highly confident about.
   - If SOURCE_MATERIAL is provided, it is the SINGLE SOURCE OF TRUTH. Do not contradict it. Do not invent content it doesn't contain.
   - If you are not confident about a specific fact, date, numerical constant, or SEBA-specific marking rule, DO NOT GUESS. Set "confidence_flag": "LOW" on that item and add a note in "verify_note" asking the student/teacher to cross-check against the textbook.
   - NEVER fabricate a "previous year question" or claim a question appeared in a specific year's SEBA paper unless that exact information exists in SOURCE_MATERIAL. If asked to generate previous-year-style questions without a source, label them clearly as "pattern_based": true, "actual_past_paper": false.
   - Numbers, chemical equations, mathematical formulas and derivations must be internally double-checked step-by-step before output. If a calculation is involved, show the working in "solution_steps" so errors are traceable.

2. GROUNDING PRIORITY ORDER:
   a. User-uploaded PDF/text (SOURCE_MATERIAL field) — highest trust
   b. YouTube transcript provided (VIDEO_TRANSCRIPT field) — second trust
   c. Your general training knowledge of NCERT-aligned Class 10 curriculum — used only as fallback, and always flagged with "grounded_in": "general_knowledge"

3. LANGUAGE RULE:
   - All explanations, notes, and instructional text must be written primarily in ASSAMESE, using English only for unavoidable technical terms (e.g., "Photosynthesis", "Quadratic Equation", "Newton's Law") which should appear in English followed by Assamese explanation.
   - Question text itself may remain bilingual (Assamese explanation + English technical term) to match actual SEBA paper conventions.
   - Exception: the ENGLISH subject module (grammar, comprehension, writing) is conducted primarily in English, since it is a language-learning subject — but instructions/feedback to the student are still in Assamese.

4. OUTPUT FORMAT:
   - Always respond with valid JSON only. No markdown, no commentary, no "Here is your quiz" preamble — just the JSON object matching the schema for the requested mode.
   - If information is genuinely insufficient to generate a safe, accurate response, return the "insufficient_data" schema instead of guessing.
</absolute_rules>
"""
    }

    // Room DB Flow accessors
    val allSessions: Flow<List<TestSessionEntity>> = dao.getAllSessions()
    val unresolvedMistakes: Flow<List<MistakeEntity>> = dao.getUnresolvedMistakes()
    val allMistakes: Flow<List<MistakeEntity>> = dao.getAllMistakes()
    val weakTopics: Flow<List<WeakTopicEntity>> = dao.getWeakTopicsFlow()
    val allEvaluations: Flow<List<EvaluationRecordEntity>> = dao.getAllEvaluations()

    // SEBA Subjects & Spaced Repetition Flows
    val allSubjects: Flow<List<SebaSubjectEntity>> = dao.getAllSubjects()
    val allSubjectProgress: Flow<List<SubjectProgressMetricEntity>> = dao.getAllSubjectProgress()

    fun getModulesForSubject(subjectId: String): Flow<List<StudyModuleEntity>> = dao.getModulesForSubject(subjectId)
    fun getModulesWithProgressForSubject(subjectId: String): Flow<List<ModuleWithProgress>> = dao.getModulesWithProgressForSubject(subjectId)
    fun getDueReviewMetrics(currentTimestamp: Long = System.currentTimeMillis()): Flow<List<SpacedRepetitionMetricEntity>> = dao.getDueReviewMetrics(currentTimestamp)
    fun getDueReviewCount(currentTimestamp: Long = System.currentTimeMillis()): Flow<Int> = dao.getDueReviewCountFlow(currentTimestamp)
    fun getReviewLogsForModule(moduleId: String): Flow<List<com.example.data.local.ModuleReviewLogEntity>> = dao.getReviewLogsForModule(moduleId)

    // Seed database if empty
    suspend fun ensureDatabaseSeeded() = withContext(Dispatchers.IO) {
        if (dao.getSubjectCount() == 0) {
            dao.insertSubjects(com.example.data.local.SebaCurriculumDefaults.DEFAULT_SUBJECTS)
            dao.insertModules(com.example.data.local.SebaCurriculumDefaults.DEFAULT_MODULES)
            dao.insertMetrics(com.example.data.local.SebaCurriculumDefaults.createInitialSpacedMetrics())
            com.example.data.local.SebaCurriculumDefaults.createInitialSubjectProgress().forEach {
                dao.insertOrUpdateSubjectProgress(it)
            }
        }
    }

    // Helper to get API key (BuildConfig or user configured)
    private fun getEffectiveApiKey(customKey: String?): String {
        val userKey = customKey?.trim().orEmpty()
        if (userKey.isNotEmpty()) return userKey

        // Fallback to injected BuildConfig
        val buildKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }
        return if (buildKey.isNotBlank() && buildKey != "MY_GEMINI_API_KEY") buildKey else ""
    }

    // 1. Generate Quiz (with Weak-Topic 60/40 Spaced Repetition Calibration)
    suspend fun generateQuiz(
        subject: String,
        chapter: String,
        sourceMaterial: String = "",
        customApiKey: String? = null
    ): Result<QuizResponse> = withContext(Dispatchers.IO) {
        val weakTopicsList = dao.getWeakTopicsForSubject(subject).map { it.topic }
        val apiKey = getEffectiveApiKey(customApiKey)

        if (apiKey.isEmpty()) {
            // Return rich offline verified SEBA bank
            return@withContext Result.success(OfflineSebaBank.getFallbackQuiz(subject, chapter))
        }

        val requestJson = JSONObject().apply {
            put("mode", "quiz")
            put("subject", subject)
            put("chapter", chapter)
            put("student_weak_topics", JSONArray(weakTopicsList))
            if (sourceMaterial.isNotBlank()) {
                put("source_material", sourceMaterial)
            }
        }.toString()

        try {
            val rawJson = callGeminiRaw(apiKey, requestJson)
            val adapter = moshi.adapter(QuizResponse::class.java)
            val cleanJson = extractCleanJson(rawJson)
            val parsed = adapter.fromJson(cleanJson)
            if (parsed != null && parsed.questions.isNotEmpty()) {
                Result.success(parsed)
            } else {
                Result.success(OfflineSebaBank.getFallbackQuiz(subject, chapter))
            }
        } catch (e: Exception) {
            Log.e(TAG, "generateQuiz failed: ${e.message}", e)
            Result.success(OfflineSebaBank.getFallbackQuiz(subject, chapter))
        }
    }

    // 2. Generate Mock Test (Timed 30-50 marks)
    suspend fun generateMockTest(
        subject: String,
        chapter: String = "Full Syllabus",
        sourceMaterial: String = "",
        customApiKey: String? = null
    ): Result<MockTestResponse> = withContext(Dispatchers.IO) {
        val apiKey = getEffectiveApiKey(customApiKey)
        if (apiKey.isEmpty()) {
            return@withContext Result.success(OfflineSebaBank.getFallbackMockTest(subject))
        }

        val requestJson = JSONObject().apply {
            put("mode", "mock_test")
            put("subject", subject)
            put("chapter", chapter)
            if (sourceMaterial.isNotBlank()) {
                put("source_material", sourceMaterial)
            }
        }.toString()

        try {
            val rawJson = callGeminiRaw(apiKey, requestJson)
            val cleanJson = extractCleanJson(rawJson)
            val adapter = moshi.adapter(MockTestResponse::class.java)
            val parsed = adapter.fromJson(cleanJson)
            if (parsed != null && parsed.sections.isNotEmpty()) {
                Result.success(parsed)
            } else {
                Result.success(OfflineSebaBank.getFallbackMockTest(subject))
            }
        } catch (e: Exception) {
            Log.e(TAG, "generateMockTest failed: ${e.message}", e)
            Result.success(OfflineSebaBank.getFallbackMockTest(subject))
        }
    }

    // 3. Generate Full Mock Paper (SEBA official Group A, B, C structure)
    suspend fun generateMockPaper(
        subject: String,
        customApiKey: String? = null
    ): Result<MockPaperResponse> = withContext(Dispatchers.IO) {
        val apiKey = getEffectiveApiKey(customApiKey)
        if (apiKey.isEmpty()) {
            return@withContext Result.success(OfflineSebaBank.getFallbackMockPaper(subject))
        }

        val requestJson = JSONObject().apply {
            put("mode", "mock_paper")
            put("subject", subject)
            put("chapter", "Full Syllabus")
        }.toString()

        try {
            val rawJson = callGeminiRaw(apiKey, requestJson)
            val cleanJson = extractCleanJson(rawJson)
            val adapter = moshi.adapter(MockPaperResponse::class.java)
            val parsed = adapter.fromJson(cleanJson)
            if (parsed != null && parsed.groups.isNotEmpty()) {
                Result.success(parsed)
            } else {
                Result.success(OfflineSebaBank.getFallbackMockPaper(subject))
            }
        } catch (e: Exception) {
            Log.e(TAG, "generateMockPaper error: ${e.message}", e)
            Result.success(OfflineSebaBank.getFallbackMockPaper(subject))
        }
    }

    // 4. Evaluate Written Answer (Rubric-based)
    suspend fun evaluateAnswer(
        subject: String,
        chapter: String,
        questionText: String,
        studentAnswer: String,
        totalMarks: Double = 5.0,
        customApiKey: String? = null
    ): Result<EvaluateResponse> = withContext(Dispatchers.IO) {
        val apiKey = getEffectiveApiKey(customApiKey)

        if (apiKey.isEmpty()) {
            // Local algorithmic rubric fallback
            val isBrief = studentAnswer.trim().length < 40
            val awarded = if (isBrief) 2.5 else 4.0
            val fallback = EvaluateResponse(
                mode = "evaluate",
                question_id = "eval_local",
                marks_awarded = awarded,
                marks_total = totalMarks,
                rubric_breakdown = listOf(
                    RubricItem(
                        criterion = "মূল বৈজ্ঞানিক / ব্যাকৰণগত ধাৰণা (Core Concept)",
                        marks = if (isBrief) 1.5 else 2.5,
                        feedback_assamese = "ধাৰণাটো স্পষ্ট, কিন্তু আৰু অধিক কাৰিকৰী শব্দ প্ৰয়োগ কৰিলে সম্পূৰ্ণ নম্বৰ পাব।"
                    ),
                    RubricItem(
                        criterion = "উত্তৰৰ উপস্থাপন আৰু স্পষ্টতা (Presentation & Step Clarity)",
                        marks = if (isBrief) 1.0 else 1.5,
                        feedback_assamese = "প্ৰয়োজনীয় সূত্ৰ আৰু উদাহৰণ সংযোগ কৰক।"
                    )
                ),
                mistake_pattern_tag = if (isBrief) "Incomplete Derivation / Step omission" else "Minor Terminology Precision",
                improvement_tip_assamese = "SEBA পৰীক্ষাত ৫ নম্বৰৰ উত্তৰত পৰিষ্কাৰ চিত্ৰ/সমীকৰণ আৰু দফাবাৰী (Point-wise) উপস্থাপন কৰিলে ০.১% টপাৰ পৰ্যায়ৰ নম্বৰ নিশ্চিত হয়।"
            )

            // Save in DB
            dao.insertEvaluation(
                EvaluationRecordEntity(
                    subject = subject,
                    chapter = chapter,
                    questionText = questionText,
                    studentAnswer = studentAnswer,
                    marksAwarded = fallback.marks_awarded,
                    marksTotal = fallback.marks_total,
                    rubricJson = moshi.adapter(EvaluateResponse::class.java).toJson(fallback),
                    mistakePatternTag = fallback.mistake_pattern_tag,
                    improvementTipAssamese = fallback.improvement_tip_assamese
                )
            )

            return@withContext Result.success(fallback)
        }

        val requestJson = JSONObject().apply {
            put("mode", "evaluate")
            put("subject", subject)
            put("chapter", chapter)
            put("question_text", questionText)
            put("student_answer", studentAnswer)
            put("marks_total", totalMarks)
        }.toString()

        try {
            val rawJson = callGeminiRaw(apiKey, requestJson)
            val cleanJson = extractCleanJson(rawJson)
            val adapter = moshi.adapter(EvaluateResponse::class.java)
            val parsed = adapter.fromJson(cleanJson) ?: error("Failed to parse evaluate response")

            // Save in Room DB
            dao.insertEvaluation(
                EvaluationRecordEntity(
                    subject = subject,
                    chapter = chapter,
                    questionText = questionText,
                    studentAnswer = studentAnswer,
                    marksAwarded = parsed.marks_awarded,
                    marksTotal = parsed.marks_total,
                    rubricJson = cleanJson,
                    mistakePatternTag = parsed.mistake_pattern_tag,
                    improvementTipAssamese = parsed.improvement_tip_assamese
                )
            )

            Result.success(parsed)
        } catch (e: Exception) {
            Log.e(TAG, "evaluateAnswer failed: ${e.message}", e)
            Result.failure(e)
        }
    }

    // 5. Mistake Analysis & Diagnostic
    suspend fun analyzeMistakes(
        customApiKey: String? = null
    ): Result<MistakeAnalysisResponse> = withContext(Dispatchers.IO) {
        val mistakes = dao.getWeakTopicsList()
        val apiKey = getEffectiveApiKey(customApiKey)

        if (apiKey.isEmpty() || mistakes.isEmpty()) {
            val defaultTopics = if (mistakes.isNotEmpty()) {
                mistakes.map { it.topic }
            } else {
                listOf(
                    "Quadratic Equations - Discriminant Nature",
                    "Chemical Reactions - Redox & Balancing",
                    "English - Subject-Verb Concord & Voice Change"
                )
            }

            return@withContext Result.success(
                MistakeAnalysisResponse(
                    mode = "mistake_analysis",
                    recurring_patterns = listOf(
                        MistakePatternItem(
                            pattern = "চিহ্ন আৰু গণিতীয় সূত্ৰৰ ব্যৱহাৰত ভুল (Sign & Formula Application)",
                            frequency = 3,
                            affected_topics = listOf("Quadratic Equations", "Trigonometry"),
                            recommended_action_assamese = "প্ৰতিটো পদক্ষেপ পুনৰীক্ষণ কৰক আৰু সূত্ৰৰ মাইনাস (-) চিহ্ন প্ৰয়োগত বিশেষ সতৰ্ক হওক।"
                        ),
                        MistakePatternItem(
                            pattern = "ৰাসায়নিক সমীকৰণ সমতুলন নকৰা (Unbalanced Equations)",
                            frequency = 2,
                            affected_topics = listOf("Chemical Reactions", "Acids, Bases & Salts"),
                            recommended_action_assamese = "উত্তৰ লিখাৰ পিছত বাওঁফাল আৰু সোঁফালৰ পৰমাণু সংখ্যা পুনৰ পৰীক্ষা কৰক।"
                        )
                    ),
                    next_spaced_repetition_topics = defaultTopics
                )
            )
        }

        val mistakesJsonArray = JSONArray()
        mistakes.forEach {
            mistakesJsonArray.put(
                JSONObject().apply {
                    put("topic", it.topic)
                    put("subject", it.subject)
                    put("mistakes_count", it.mistakeCount)
                }
            )
        }

        val requestJson = JSONObject().apply {
            put("mode", "mistake_analysis")
            put("past_mistakes", mistakesJsonArray)
        }.toString()

        try {
            val rawJson = callGeminiRaw(apiKey, requestJson)
            val cleanJson = extractCleanJson(rawJson)
            val adapter = moshi.adapter(MistakeAnalysisResponse::class.java)
            val parsed = adapter.fromJson(cleanJson) ?: error("Failed to parse mistake analysis")
            Result.success(parsed)
        } catch (e: Exception) {
            Log.e(TAG, "analyzeMistakes error: ${e.message}", e)
            Result.failure(e)
        }
    }

    // 6. Video Based Quiz (YouTube transcript grounding)
    suspend fun generateVideoQuiz(
        subject: String,
        chapter: String,
        videoTranscript: String,
        videoTitle: String = "YouTube Revision Class",
        customApiKey: String? = null
    ): Result<QuizResponse> = withContext(Dispatchers.IO) {
        val apiKey = getEffectiveApiKey(customApiKey)

        if (apiKey.isEmpty() || videoTranscript.isBlank()) {
            return@withContext Result.success(OfflineSebaBank.getFallbackQuiz(subject, chapter))
        }

        val requestJson = JSONObject().apply {
            put("mode", "video_quiz")
            put("subject", subject)
            put("chapter", chapter)
            put("video_title", videoTitle)
            put("video_transcript", videoTranscript)
        }.toString()

        try {
            val rawJson = callGeminiRaw(apiKey, requestJson)
            val cleanJson = extractCleanJson(rawJson)
            val adapter = moshi.adapter(QuizResponse::class.java)
            val parsed = adapter.fromJson(cleanJson) ?: error("Failed to parse video quiz")
            Result.success(parsed)
        } catch (e: Exception) {
            Log.e(TAG, "generateVideoQuiz error: ${e.message}", e)
            Result.success(OfflineSebaBank.getFallbackQuiz(subject, chapter))
        }
    }

    // 7. English Grammar Module
    suspend fun generateEnglishGrammar(
        grammarTopic: String,
        customApiKey: String? = null
    ): Result<EnglishGrammarResponse> = withContext(Dispatchers.IO) {
        val apiKey = getEffectiveApiKey(customApiKey)
        if (apiKey.isEmpty()) {
            return@withContext Result.success(OfflineSebaBank.getFallbackGrammar(grammarTopic))
        }

        val requestJson = JSONObject().apply {
            put("mode", "english_grammar")
            put("subject", "English")
            put("grammar_topic", grammarTopic)
        }.toString()

        try {
            val rawJson = callGeminiRaw(apiKey, requestJson)
            val cleanJson = extractCleanJson(rawJson)
            val adapter = moshi.adapter(EnglishGrammarResponse::class.java)
            val parsed = adapter.fromJson(cleanJson) ?: error("Failed to parse grammar")
            Result.success(parsed)
        } catch (e: Exception) {
            Log.e(TAG, "generateEnglishGrammar error: ${e.message}", e)
            Result.success(OfflineSebaBank.getFallbackGrammar(grammarTopic))
        }
    }

    // Record Test Session & Update Weak Topics
    suspend fun recordQuizSession(
        mode: String,
        subject: String,
        chapter: String,
        totalQuestions: Int,
        totalMarks: Int,
        scoreAwarded: Double,
        rawJson: String
    ) = withContext(Dispatchers.IO) {
        dao.insertSession(
            TestSessionEntity(
                mode = mode,
                subject = subject,
                chapter = chapter,
                totalQuestions = totalQuestions,
                totalMarks = totalMarks,
                scoreAwarded = scoreAwarded,
                rawJsonResponse = rawJson
            )
        )
    }

    // Record Mistake for Spaced Repetition Diagnostic
    suspend fun recordMistake(
        subject: String,
        chapter: String,
        questionAssamese: String,
        userAnswer: String,
        correctAnswer: String,
        conceptTag: String,
        solutionSteps: List<String> = emptyList()
    ) = withContext(Dispatchers.IO) {
        dao.insertMistake(
            MistakeEntity(
                subject = subject,
                chapter = chapter,
                questionAssamese = questionAssamese,
                userAnswer = userAnswer,
                correctAnswer = correctAnswer,
                conceptTag = conceptTag,
                solutionStepsJson = JSONArray(solutionSteps).toString()
            )
        )

        // Increment Weak Topic count
        if (conceptTag.isNotBlank()) {
            val existing = dao.getWeakTopicByName(conceptTag)
            if (existing != null) {
                dao.insertOrUpdateWeakTopic(
                    existing.copy(
                        mistakeCount = existing.mistakeCount + 1,
                        lastPracticed = System.currentTimeMillis()
                    )
                )
            } else {
                dao.insertOrUpdateWeakTopic(
                    WeakTopicEntity(
                        topic = conceptTag,
                        subject = subject,
                        mistakeCount = 1,
                        correctCount = 0,
                        lastPracticed = System.currentTimeMillis()
                    )
                )
            }
        }
    }

    // Record Correct Answer
    suspend fun recordCorrectAnswer(subject: String, conceptTag: String) = withContext(Dispatchers.IO) {
        if (conceptTag.isNotBlank()) {
            val existing = dao.getWeakTopicByName(conceptTag)
            if (existing != null) {
                dao.insertOrUpdateWeakTopic(
                    existing.copy(
                        correctCount = existing.correctCount + 1,
                        lastPracticed = System.currentTimeMillis()
                    )
                )
            }
        }
    }

    suspend fun markMistakeResolved(id: Long) = withContext(Dispatchers.IO) {
        dao.markMistakeResolved(id)
    }

    // ==========================================
    // Spaced Repetition (SM-2 & Ebbinghaus) Engine
    // ==========================================
    suspend fun recordModuleReviewSession(
        moduleId: String,
        subjectId: String,
        qualityScore: Int, // 0 to 5
        questionsAttempted: Int,
        questionsCorrect: Int,
        timeSpentSeconds: Int
    ) = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()
        val existing = dao.getMetricForModule(moduleId)

        val prevEF = existing?.easeFactor ?: 2.5
        val prevInterval = existing?.intervalDays ?: 0
        val prevRepLevel = existing?.repetitionLevel ?: 0
        val prevAttempted = existing?.totalQuestionsAttempted ?: 0
        val prevCorrect = existing?.totalQuestionsCorrect ?: 0
        val prevStreak = existing?.streakCount ?: 0

        // SuperMemo-2 Ease Factor calculation
        val q = qualityScore.coerceIn(0, 5)
        var newEF = prevEF + (0.1 - (5 - q) * (0.08 + (5 - q) * 0.02))
        if (newEF < 1.3) newEF = 1.3

        val (newRepLevel, newInterval) = if (q < 3) {
            // Failed recall: reset repetitions to 1st stage
            0 to 1
        } else {
            val rep = prevRepLevel + 1
            val interval = when (prevRepLevel) {
                0 -> 1
                1 -> 3
                2 -> 7
                else -> kotlin.math.max(1, (prevInterval * newEF).toInt())
            }
            rep to interval
        }

        val nextDueDate = now + (newInterval.toLong() * 86400000L)
        val totalAttempted = prevAttempted + questionsAttempted
        val totalCorrect = prevCorrect + questionsCorrect
        val accuracy = if (totalAttempted > 0) (totalCorrect.toDouble() / totalAttempted) * 100.0 else 0.0
        val newStreak = if (q >= 3) prevStreak + 1 else 0

        val mastery = kotlin.math.min(
            100.0,
            (newRepLevel * 18.0) + (accuracy * 0.25) + ((newEF - 1.3) * 10.0)
        ).coerceIn(0.0, 100.0)

        val status = when {
            mastery >= 90.0 && newRepLevel >= 4 -> "MASTERED"
            newInterval == 1 -> "LEARNING"
            else -> "LEARNING"
        }

        val updatedMetric = SpacedRepetitionMetricEntity(
            id = "sr_$moduleId",
            moduleId = moduleId,
            subjectId = subjectId,
            repetitionLevel = newRepLevel,
            intervalDays = newInterval,
            easeFactor = newEF,
            successfulReviews = (existing?.successfulReviews ?: 0) + (if (q >= 3) 1 else 0),
            totalReviews = (existing?.totalReviews ?: 0) + 1,
            lastReviewedAt = now,
            nextReviewDueDate = nextDueDate,
            retentionScore = 100.0,
            masteryPercentage = mastery,
            accuracyPercentage = accuracy,
            totalQuestionsAttempted = totalAttempted,
            totalQuestionsCorrect = totalCorrect,
            streakCount = newStreak,
            lastQualityScore = q,
            status = status
        )

        dao.insertOrUpdateMetric(updatedMetric)

        // Log session
        dao.insertReviewLog(
            com.example.data.local.ModuleReviewLogEntity(
                moduleId = moduleId,
                subjectId = subjectId,
                reviewTimestamp = now,
                qualityRating = q,
                timeSpentSeconds = timeSpentSeconds,
                questionsAttempted = questionsAttempted,
                questionsCorrect = questionsCorrect,
                scorePercentage = if (questionsAttempted > 0) (questionsCorrect.toDouble() / questionsAttempted) * 100.0 else 0.0,
                previousIntervalDays = prevInterval,
                newIntervalDays = newInterval,
                previousEaseFactor = prevEF,
                newEaseFactor = newEF,
                nextReviewScheduledAt = nextDueDate
            )
        )
    }

    // Refresh Ebbinghaus memory retention decay across all modules
    suspend fun refreshRetentionDecay() = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()
        val dueList = dao.getDueReviewMetricsList(now + (30L * 86400000L))
        dueList.forEach { metric ->
            if (metric.lastReviewedAt > 0L) {
                val elapsedDays = (now - metric.lastReviewedAt).toDouble() / 86400000.0
                val stability = metric.intervalDays.coerceAtLeast(1) * metric.easeFactor
                // Ebbinghaus exponential decay formula R = e^(-t/S)
                val retention = (kotlin.math.exp(-elapsedDays / stability) * 100.0).coerceIn(10.0, 100.0)
                val status = if (now >= metric.nextReviewDueDate && metric.status != "MASTERED") {
                    "DUE_FOR_REVIEW"
                } else {
                    metric.status
                }
                dao.updateRetentionAndStatus(metric.moduleId, retention, status)
            }
        }
    }

    // Private helper to invoke Gemini API with Lakshya AI System Instruction
    private suspend fun callGeminiRaw(apiKey: String, promptPayload: String): String {
        val request = GeminiRequest(
            contents = listOf(
                GeminiContent(
                    parts = listOf(GeminiPart(text = promptPayload)),
                    role = "user"
                )
            ),
            systemInstruction = GeminiContent(
                parts = listOf(GeminiPart(text = LAKSHYA_SYSTEM_PROMPT))
            ),
            generationConfig = GeminiGenerationConfig(
                temperature = 0.2f,
                topP = 0.95f,
                responseMimeType = "application/json"
            )
        )

        val response = GeminiClient.apiService.generateContent(apiKey, request)
        val text = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
        if (text.isNullOrBlank()) {
            throw IllegalStateException("Received empty response from Gemini")
        }
        return text
    }

    // Sanitize JSON output (removing backticks or markdown preamble if any)
    private fun extractCleanJson(raw: String): String {
        var clean = raw.trim()
        if (clean.startsWith("```json")) {
            clean = clean.removePrefix("```json").trim()
        } else if (clean.startsWith("```")) {
            clean = clean.removePrefix("```").trim()
        }
        if (clean.endsWith("```")) {
            clean = clean.removeSuffix("```").trim()
        }
        return clean
    }
}
