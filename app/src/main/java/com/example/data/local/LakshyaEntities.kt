package com.example.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// ==========================================
// 1. SEBA Class 10 Core Subjects
// ==========================================
@Entity(
    tableName = "seba_subjects",
    indices = [Index(value = ["code"], unique = true)]
)
data class SebaSubjectEntity(
    @PrimaryKey val id: String, // e.g. "mathematics", "science", "social_science", "english", "assamese"
    val code: String, // e.g. "C1", "C2", "C3", "C4", "IL1"
    val titleEnglish: String, // e.g. "Mathematics"
    val titleAssamese: String, // e.g. "গণিত"
    val totalMarks: Int = 100,
    val theoryMarks: Int = 90,
    val internalAssessmentMarks: Int = 10,
    val passMarks: Int = 30,
    val iconKey: String = "menu_book", // Icon identifier
    val colorHex: String = "#1E3A8A", // Theme color
    val displayOrder: Int = 0
)

// ==========================================
// 2. SEBA Class 10 Study Modules / Chapters
// ==========================================
@Entity(
    tableName = "study_modules",
    foreignKeys = [
        ForeignKey(
            entity = SebaSubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["subjectId"]),
        Index(value = ["subjectId", "chapterNumber"])
    ]
)
data class StudyModuleEntity(
    @PrimaryKey val id: String, // e.g. "math_ch1", "sci_ch1", "soc_ch1"
    val subjectId: String, // references SebaSubjectEntity.id
    val chapterNumber: Int,
    val titleEnglish: String,
    val titleAssamese: String,
    val subtopicsJson: String = "[]", // List of subtopics
    val keyFormulasOrPointsJson: String = "[]", // High-yield key points / formulas
    val weightageMarks: Int = 6, // Approximate marks weightage in SEBA HSLC
    val difficultyLevel: String = "MEDIUM", // EASY | MEDIUM | HARD
    val isHighYield: Boolean = true, // Frequently asked in board exams
    val orderIndex: Int = 0
)

// ==========================================
// 3. User Progress Metrics & Spaced Repetition (SM-2 & Leitner)
// ==========================================
@Entity(
    tableName = "spaced_repetition_metrics",
    foreignKeys = [
        ForeignKey(
            entity = StudyModuleEntity::class,
            parentColumns = ["id"],
            childColumns = ["moduleId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["moduleId"], unique = true),
        Index(value = ["subjectId"]),
        Index(value = ["nextReviewDueDate"]),
        Index(value = ["status"])
    ]
)
data class SpacedRepetitionMetricEntity(
    @PrimaryKey val id: String, // e.g. "sr_math_ch1"
    val moduleId: String, // references StudyModuleEntity.id
    val subjectId: String,
    val repetitionLevel: Int = 0, // 0 = New, 1 = 1 day, 2 = 3 days, 3 = 7 days, 4 = 14 days, 5 = 30 days, 6 = 60 days
    val intervalDays: Int = 0, // Current interval in days
    val easeFactor: Double = 2.5, // SuperMemo-2 ease factor (default 2.5, min 1.3)
    val successfulReviews: Int = 0,
    val totalReviews: Int = 0,
    val lastReviewedAt: Long = 0L, // Timestamp in milliseconds
    val nextReviewDueDate: Long = 0L, // Timestamp in milliseconds
    val retentionScore: Double = 100.0, // Estimated memory retention % (Ebbinghaus curve: R = e^(-t/S))
    val masteryPercentage: Double = 0.0, // 0.0 to 100.0%
    val accuracyPercentage: Double = 0.0, // Accuracy across all questions attempted
    val totalQuestionsAttempted: Int = 0,
    val totalQuestionsCorrect: Int = 0,
    val streakCount: Int = 0,
    val lastQualityScore: Int = 0, // 0 to 5 SM-2 rating
    val status: String = "NEW" // NEW | LEARNING | DUE_FOR_REVIEW | MASTERED | OVERDUE
)

// ==========================================
// 4. Detailed Module Review Session Log
// ==========================================
@Entity(
    tableName = "module_review_logs",
    foreignKeys = [
        ForeignKey(
            entity = StudyModuleEntity::class,
            parentColumns = ["id"],
            childColumns = ["moduleId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["moduleId"]),
        Index(value = ["subjectId"]),
        Index(value = ["reviewTimestamp"])
    ]
)
data class ModuleReviewLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val moduleId: String,
    val subjectId: String,
    val reviewTimestamp: Long = System.currentTimeMillis(),
    val qualityRating: Int = 5, // 0 (blackout) to 5 (perfect recall)
    val timeSpentSeconds: Int = 0,
    val questionsAttempted: Int = 0,
    val questionsCorrect: Int = 0,
    val scorePercentage: Double = 0.0,
    val previousIntervalDays: Int = 0,
    val newIntervalDays: Int = 1,
    val previousEaseFactor: Double = 2.5,
    val newEaseFactor: Double = 2.5,
    val nextReviewScheduledAt: Long = 0L
)

// ==========================================
// 5. Subject Aggregated Progress Metrics
// ==========================================
@Entity(
    tableName = "subject_progress_metrics",
    foreignKeys = [
        ForeignKey(
            entity = SebaSubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SubjectProgressMetricEntity(
    @PrimaryKey val subjectId: String,
    val totalModules: Int = 0,
    val masteredModules: Int = 0,
    val learningModules: Int = 0,
    val dueForReviewCount: Int = 0,
    val averageMasteryPercentage: Double = 0.0,
    val averageRetentionPercentage: Double = 100.0,
    val totalTimeSpentSeconds: Long = 0L,
    val lastActiveTimestamp: Long = System.currentTimeMillis()
)

// ==========================================
// 6. Test Sessions
// ==========================================
@Entity(tableName = "test_sessions")
data class TestSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val mode: String,
    val subject: String,
    val chapter: String,
    val totalQuestions: Int,
    val totalMarks: Int,
    val scoreAwarded: Double,
    val completedAt: Long = System.currentTimeMillis(),
    val rawJsonResponse: String
)

// ==========================================
// 7. Mistakes & Spaced Repetition Diagnostics
// ==========================================
@Entity(tableName = "mistakes")
data class MistakeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subject: String,
    val chapter: String,
    val questionAssamese: String,
    val questionEnglish: String = "",
    val optionsJson: String = "",
    val userAnswer: String,
    val correctAnswer: String,
    val solutionStepsJson: String = "",
    val conceptTag: String = "",
    val difficulty: String = "medium",
    val isResolved: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

// ==========================================
// 8. Weak Topics
// ==========================================
@Entity(tableName = "weak_topics")
data class WeakTopicEntity(
    @PrimaryKey val topic: String,
    val subject: String,
    val mistakeCount: Int = 0,
    val correctCount: Int = 0,
    val lastPracticed: Long = System.currentTimeMillis()
)

// ==========================================
// 9. Evaluation Records
// ==========================================
@Entity(tableName = "evaluation_records")
data class EvaluationRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subject: String,
    val chapter: String,
    val questionText: String,
    val studentAnswer: String,
    val marksAwarded: Double,
    val marksTotal: Double,
    val rubricJson: String,
    val mistakePatternTag: String,
    val improvementTipAssamese: String,
    val timestamp: Long = System.currentTimeMillis()
)
