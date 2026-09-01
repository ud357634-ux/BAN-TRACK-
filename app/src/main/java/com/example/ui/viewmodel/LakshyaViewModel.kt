package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.LakshyaDatabase
import com.example.data.local.MistakeEntity
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
import com.example.data.model.MockPaperResponse
import com.example.data.model.MockTestResponse
import com.example.data.model.QuestionItem
import com.example.data.model.QuizResponse
import com.example.data.model.SebaSubject
import com.example.data.repository.LakshyaRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface UiState<out T> {
    data object Idle : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}

class LakshyaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: LakshyaRepository
    init {
        val db = LakshyaDatabase.getInstance(application)
        repository = LakshyaRepository(db.lakshyaDao())

        // Ensure default curriculum is seeded and decay refreshed
        viewModelScope.launch {
            repository.ensureDatabaseSeeded()
            repository.refreshRetentionDecay()
        }
    }

    // Shared preferences for API Key and Source Material
    private val prefs = application.getSharedPreferences("lakshya_prefs", Application.MODE_PRIVATE)

    private val _customApiKey = MutableStateFlow(prefs.getString("custom_api_key", "").orEmpty())
    val customApiKey: StateFlow<String> = _customApiKey.asStateFlow()

    private val _sourceMaterial = MutableStateFlow(prefs.getString("source_material", "").orEmpty())
    val sourceMaterial: StateFlow<String> = _sourceMaterial.asStateFlow()

    // Room DB Streams
    val testSessions: StateFlow<List<TestSessionEntity>> = repository.allSessions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unresolvedMistakes: StateFlow<List<MistakeEntity>> = repository.unresolvedMistakes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val weakTopics: StateFlow<List<WeakTopicEntity>> = repository.weakTopics
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // SEBA Subjects & Spaced Repetition Streams
    val allSubjects: StateFlow<List<SebaSubjectEntity>> = repository.allSubjects
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSubjectProgress: StateFlow<List<SubjectProgressMetricEntity>> = repository.allSubjectProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dueReviews: StateFlow<List<SpacedRepetitionMetricEntity>> = repository.getDueReviewMetrics()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dueReviewCount: StateFlow<Int> = repository.getDueReviewCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun getModulesWithProgress(subjectId: String): kotlinx.coroutines.flow.Flow<List<ModuleWithProgress>> {
        return repository.getModulesWithProgressForSubject(subjectId)
    }

    fun recordSpacedReview(
        moduleId: String,
        subjectId: String,
        qualityScore: Int,
        questionsAttempted: Int,
        questionsCorrect: Int,
        timeSpentSeconds: Int
    ) {
        viewModelScope.launch {
            repository.recordModuleReviewSession(
                moduleId = moduleId,
                subjectId = subjectId,
                qualityScore = qualityScore,
                questionsAttempted = questionsAttempted,
                questionsCorrect = questionsCorrect,
                timeSpentSeconds = timeSpentSeconds
            )
        }
    }

    // Active Quiz State
    private val _quizState = MutableStateFlow<UiState<QuizResponse>>(UiState.Idle)
    val quizState: StateFlow<UiState<QuizResponse>> = _quizState.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _selectedAnswers = MutableStateFlow<Map<Int, String>>(emptyMap())
    val selectedAnswers: StateFlow<Map<Int, String>> = _selectedAnswers.asStateFlow()

    private val _quizSubmitted = MutableStateFlow(false)
    val quizSubmitted: StateFlow<Boolean> = _quizSubmitted.asStateFlow()

    private val _quizScore = MutableStateFlow(0)
    val quizScore: StateFlow<Int> = _quizScore.asStateFlow()

    // Mock Test State
    private val _mockTestState = MutableStateFlow<UiState<MockTestResponse>>(UiState.Idle)
    val mockTestState: StateFlow<UiState<MockTestResponse>> = _mockTestState.asStateFlow()

    private val _mockTimeRemainingSeconds = MutableStateFlow(0)
    val mockTimeRemainingSeconds: StateFlow<Int> = _mockTimeRemainingSeconds.asStateFlow()
    private var timerJob: Job? = null

    // Mock Paper State
    private val _mockPaperState = MutableStateFlow<UiState<MockPaperResponse>>(UiState.Idle)
    val mockPaperState: StateFlow<UiState<MockPaperResponse>> = _mockPaperState.asStateFlow()

    // Evaluate Written Answer State
    private val _evaluateState = MutableStateFlow<UiState<EvaluateResponse>>(UiState.Idle)
    val evaluateState: StateFlow<UiState<EvaluateResponse>> = _evaluateState.asStateFlow()

    // Mistake Diagnostic State
    private val _mistakeAnalysisState = MutableStateFlow<UiState<MistakeAnalysisResponse>>(UiState.Idle)
    val mistakeAnalysisState: StateFlow<UiState<MistakeAnalysisResponse>> = _mistakeAnalysisState.asStateFlow()

    // English Grammar State
    private val _grammarState = MutableStateFlow<UiState<EnglishGrammarResponse>>(UiState.Idle)
    val grammarState: StateFlow<UiState<EnglishGrammarResponse>> = _grammarState.asStateFlow()

    // Video Quiz State
    private val _videoQuizState = MutableStateFlow<UiState<QuizResponse>>(UiState.Idle)
    val videoQuizState: StateFlow<UiState<QuizResponse>> = _videoQuizState.asStateFlow()

    fun setCustomApiKey(key: String) {
        _customApiKey.value = key
        prefs.edit().putString("custom_api_key", key).apply()
    }

    fun setSourceMaterial(text: String) {
        _sourceMaterial.value = text
        prefs.edit().putString("source_material", text).apply()
    }

    // 1. Launch Practice Quiz
    fun startQuiz(subject: String, chapter: String) {
        viewModelScope.launch {
            _quizState.value = UiState.Loading
            _currentQuestionIndex.value = 0
            _selectedAnswers.value = emptyMap()
            _quizSubmitted.value = false
            _quizScore.value = 0

            val result = repository.generateQuiz(
                subject = subject,
                chapter = chapter,
                sourceMaterial = _sourceMaterial.value,
                customApiKey = _customApiKey.value
            )

            result.onSuccess { response ->
                _quizState.value = UiState.Success(response)
            }.onFailure { error ->
                _quizState.value = UiState.Error(error.message ?: "Failed to generate quiz")
            }
        }
    }

    fun selectQuizAnswer(questionIndex: Int, answer: String) {
        if (_quizSubmitted.value) return
        val current = _selectedAnswers.value.toMutableMap()
        current[questionIndex] = answer
        _selectedAnswers.value = current
    }

    fun submitQuiz() {
        val state = _quizState.value
        if (state !is UiState.Success) return
        val quiz = state.data

        var calculatedScore = 0
        quiz.questions.forEachIndexed { index, question ->
            val userAns = _selectedAnswers.value[index].orEmpty().trim()
            val isCorrect = if (question.type == "MCQ" || question.type == "true_false") {
                userAns.startsWith(question.correct_answer.take(2), ignoreCase = true) ||
                        userAns.equals(question.correct_answer, ignoreCase = true)
            } else {
                userAns.isNotBlank() && (question.correct_answer.contains(userAns, ignoreCase = true) || userAns.contains(question.correct_answer, ignoreCase = true))
            }

            if (isCorrect) {
                calculatedScore += question.marks
                viewModelScope.launch {
                    repository.recordCorrectAnswer(quiz.subject, question.concept_tag)
                }
            } else if (userAns.isNotBlank()) {
                viewModelScope.launch {
                    repository.recordMistake(
                        subject = quiz.subject,
                        chapter = quiz.chapter,
                        questionAssamese = question.question_assamese,
                        userAnswer = userAns,
                        correctAnswer = question.correct_answer,
                        conceptTag = question.concept_tag,
                        solutionSteps = question.solution_steps
                    )
                }
            }
        }

        _quizScore.value = calculatedScore
        _quizSubmitted.value = true

        viewModelScope.launch {
            repository.recordQuizSession(
                mode = "quiz",
                subject = quiz.subject,
                chapter = quiz.chapter,
                totalQuestions = quiz.questions.size,
                totalMarks = quiz.questions.sumOf { it.marks },
                scoreAwarded = calculatedScore.toDouble(),
                rawJson = ""
            )
        }
    }

    fun nextQuestion(max: Int) {
        if (_currentQuestionIndex.value < max - 1) {
            _currentQuestionIndex.value++
        }
    }

    fun previousQuestion() {
        if (_currentQuestionIndex.value > 0) {
            _currentQuestionIndex.value--
        }
    }

    // 2. Launch Timed Mock Test
    fun startMockTest(subject: String, chapter: String = "Full Syllabus") {
        viewModelScope.launch {
            _mockTestState.value = UiState.Loading
            timerJob?.cancel()

            val result = repository.generateMockTest(
                subject = subject,
                chapter = chapter,
                sourceMaterial = _sourceMaterial.value,
                customApiKey = _customApiKey.value
            )

            result.onSuccess { response ->
                _mockTestState.value = UiState.Success(response)
                startTimer(response.suggested_time_minutes * 60)
            }.onFailure { error ->
                _mockTestState.value = UiState.Error(error.message ?: "Failed to generate mock test")
            }
        }
    }

    private fun startTimer(totalSeconds: Int) {
        _mockTimeRemainingSeconds.value = totalSeconds
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_mockTimeRemainingSeconds.value > 0) {
                delay(1000)
                _mockTimeRemainingSeconds.value--
            }
        }
    }

    // 3. Launch Full Mock Paper
    fun startMockPaper(subject: String) {
        viewModelScope.launch {
            _mockPaperState.value = UiState.Loading
            val result = repository.generateMockPaper(
                subject = subject,
                customApiKey = _customApiKey.value
            )
            result.onSuccess { response ->
                _mockPaperState.value = UiState.Success(response)
            }.onFailure { error ->
                _mockPaperState.value = UiState.Error(error.message ?: "Failed to generate mock paper")
            }
        }
    }

    // 4. Evaluate Written Answer
    fun evaluateWrittenAnswer(
        subject: String,
        chapter: String,
        questionText: String,
        studentAnswer: String,
        totalMarks: Double = 5.0
    ) {
        viewModelScope.launch {
            _evaluateState.value = UiState.Loading
            val result = repository.evaluateAnswer(
                subject = subject,
                chapter = chapter,
                questionText = questionText,
                studentAnswer = studentAnswer,
                totalMarks = totalMarks,
                customApiKey = _customApiKey.value
            )
            result.onSuccess { response ->
                _evaluateState.value = UiState.Success(response)
            }.onFailure { error ->
                _evaluateState.value = UiState.Error(error.message ?: "Evaluation failed")
            }
        }
    }

    fun resetEvaluate() {
        _evaluateState.value = UiState.Idle
    }

    // 5. Run Mistake Analysis
    fun runMistakeAnalysis() {
        viewModelScope.launch {
            _mistakeAnalysisState.value = UiState.Loading
            val result = repository.analyzeMistakes(_customApiKey.value)
            result.onSuccess { response ->
                _mistakeAnalysisState.value = UiState.Success(response)
            }.onFailure { error ->
                _mistakeAnalysisState.value = UiState.Error(error.message ?: "Analysis failed")
            }
        }
    }

    fun markMistakeResolved(id: Long) {
        viewModelScope.launch {
            repository.markMistakeResolved(id)
        }
    }

    // 6. Generate Video Quiz
    fun generateVideoQuiz(subject: String, chapter: String, transcript: String, title: String) {
        viewModelScope.launch {
            _videoQuizState.value = UiState.Loading
            val result = repository.generateVideoQuiz(
                subject = subject,
                chapter = chapter,
                videoTranscript = transcript,
                videoTitle = title,
                customApiKey = _customApiKey.value
            )
            result.onSuccess { response ->
                _videoQuizState.value = UiState.Success(response)
            }.onFailure { error ->
                _videoQuizState.value = UiState.Error(error.message ?: "Failed to generate video quiz")
            }
        }
    }

    // 7. Load English Grammar Module
    fun loadGrammarTopic(topic: String) {
        viewModelScope.launch {
            _grammarState.value = UiState.Loading
            val result = repository.generateEnglishGrammar(
                grammarTopic = topic,
                customApiKey = _customApiKey.value
            )
            result.onSuccess { response ->
                _grammarState.value = UiState.Success(response)
            }.onFailure { error ->
                _grammarState.value = UiState.Error(error.message ?: "Failed to load grammar")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
