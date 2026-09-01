package com.example.data.local

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

data class SubjectWithModules(
    @Embedded val subject: SebaSubjectEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "subjectId"
    )
    val modules: List<StudyModuleEntity>
)

data class ModuleWithProgress(
    @Embedded val module: StudyModuleEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "moduleId"
    )
    val metric: SpacedRepetitionMetricEntity?
)

@Dao
interface LakshyaDao {

    // ==========================================
    // 1. SEBA Subjects
    // ==========================================
    @Query("SELECT * FROM seba_subjects ORDER BY displayOrder ASC")
    fun getAllSubjects(): Flow<List<SebaSubjectEntity>>

    @Query("SELECT * FROM seba_subjects ORDER BY displayOrder ASC")
    suspend fun getAllSubjectsList(): List<SebaSubjectEntity>

    @Query("SELECT * FROM seba_subjects WHERE id = :subjectId LIMIT 1")
    suspend fun getSubjectById(subjectId: String): SebaSubjectEntity?

    @Query("SELECT COUNT(*) FROM seba_subjects")
    suspend fun getSubjectCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<SebaSubjectEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: SebaSubjectEntity)

    // ==========================================
    // 2. Study Modules
    // ==========================================
    @Query("SELECT * FROM study_modules WHERE subjectId = :subjectId ORDER BY orderIndex ASC, chapterNumber ASC")
    fun getModulesForSubject(subjectId: String): Flow<List<StudyModuleEntity>>

    @Query("SELECT * FROM study_modules WHERE subjectId = :subjectId ORDER BY orderIndex ASC, chapterNumber ASC")
    suspend fun getModulesForSubjectList(subjectId: String): List<StudyModuleEntity>

    @Query("SELECT * FROM study_modules ORDER BY subjectId ASC, chapterNumber ASC")
    fun getAllModules(): Flow<List<StudyModuleEntity>>

    @Query("SELECT * FROM study_modules WHERE id = :moduleId LIMIT 1")
    suspend fun getModuleById(moduleId: String): StudyModuleEntity?

    @Query("SELECT * FROM study_modules WHERE isHighYield = 1 ORDER BY weightageMarks DESC")
    fun getHighYieldModules(): Flow<List<StudyModuleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModules(modules: List<StudyModuleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModule(module: StudyModuleEntity)

    // ==========================================
    // 3. Spaced Repetition Metrics
    // ==========================================
    @Query("SELECT * FROM spaced_repetition_metrics WHERE subjectId = :subjectId")
    fun getSpacedMetricsForSubject(subjectId: String): Flow<List<SpacedRepetitionMetricEntity>>

    @Query("SELECT * FROM spaced_repetition_metrics WHERE moduleId = :moduleId LIMIT 1")
    suspend fun getMetricForModule(moduleId: String): SpacedRepetitionMetricEntity?

    @Query("SELECT * FROM spaced_repetition_metrics WHERE moduleId = :moduleId LIMIT 1")
    fun getMetricForModuleFlow(moduleId: String): Flow<SpacedRepetitionMetricEntity?>

    @Query("SELECT * FROM spaced_repetition_metrics WHERE nextReviewDueDate <= :currentTimestamp AND status != 'MASTERED' ORDER BY nextReviewDueDate ASC")
    fun getDueReviewMetrics(currentTimestamp: Long): Flow<List<SpacedRepetitionMetricEntity>>

    @Query("SELECT * FROM spaced_repetition_metrics WHERE nextReviewDueDate <= :currentTimestamp AND status != 'MASTERED' ORDER BY nextReviewDueDate ASC")
    suspend fun getDueReviewMetricsList(currentTimestamp: Long): List<SpacedRepetitionMetricEntity>

    @Query("SELECT COUNT(*) FROM spaced_repetition_metrics WHERE nextReviewDueDate <= :currentTimestamp AND status != 'MASTERED'")
    fun getDueReviewCountFlow(currentTimestamp: Long): Flow<Int>

    @Query("SELECT * FROM spaced_repetition_metrics WHERE status = 'MASTERED'")
    fun getMasteredMetrics(): Flow<List<SpacedRepetitionMetricEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateMetric(metric: SpacedRepetitionMetricEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetrics(metrics: List<SpacedRepetitionMetricEntity>)

    @Query("""
        UPDATE spaced_repetition_metrics 
        SET retentionScore = :retentionScore,
            status = :status
        WHERE moduleId = :moduleId
    """)
    suspend fun updateRetentionAndStatus(moduleId: String, retentionScore: Double, status: String)

    // ==========================================
    // 4. Combined Module + Progress Queries
    // ==========================================
    @Transaction
    @Query("SELECT * FROM study_modules WHERE subjectId = :subjectId ORDER BY chapterNumber ASC")
    fun getModulesWithProgressForSubject(subjectId: String): Flow<List<ModuleWithProgress>>

    @Transaction
    @Query("SELECT * FROM seba_subjects WHERE id = :subjectId")
    fun getSubjectWithModules(subjectId: String): Flow<SubjectWithModules?>

    // ==========================================
    // 5. Module Review Logs
    // ==========================================
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviewLog(log: ModuleReviewLogEntity): Long

    @Query("SELECT * FROM module_review_logs WHERE moduleId = :moduleId ORDER BY reviewTimestamp DESC")
    fun getReviewLogsForModule(moduleId: String): Flow<List<ModuleReviewLogEntity>>

    @Query("SELECT * FROM module_review_logs ORDER BY reviewTimestamp DESC LIMIT :limit")
    fun getRecentReviewLogs(limit: Int = 20): Flow<List<ModuleReviewLogEntity>>

    // ==========================================
    // 6. Subject Progress Aggregation
    // ==========================================
    @Query("SELECT * FROM subject_progress_metrics WHERE subjectId = :subjectId LIMIT 1")
    fun getSubjectProgress(subjectId: String): Flow<SubjectProgressMetricEntity?>

    @Query("SELECT * FROM subject_progress_metrics")
    fun getAllSubjectProgress(): Flow<List<SubjectProgressMetricEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateSubjectProgress(progress: SubjectProgressMetricEntity)

    // ==========================================
    // 7. Test Sessions
    // ==========================================
    @Query("SELECT * FROM test_sessions ORDER BY completedAt DESC")
    fun getAllSessions(): Flow<List<TestSessionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: TestSessionEntity): Long

    // ==========================================
    // 8. Mistakes Tracking
    // ==========================================
    @Query("SELECT * FROM mistakes WHERE isResolved = 0 ORDER BY timestamp DESC")
    fun getUnresolvedMistakes(): Flow<List<MistakeEntity>>

    @Query("SELECT * FROM mistakes ORDER BY timestamp DESC")
    fun getAllMistakes(): Flow<List<MistakeEntity>>

    @Query("SELECT * FROM mistakes WHERE subject = :subject AND isResolved = 0 ORDER BY timestamp DESC")
    fun getMistakesBySubject(subject: String): Flow<List<MistakeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMistake(mistake: MistakeEntity): Long

    @Update
    suspend fun updateMistake(mistake: MistakeEntity)

    @Query("UPDATE mistakes SET isResolved = 1 WHERE id = :id")
    suspend fun markMistakeResolved(id: Long)

    @Query("DELETE FROM mistakes WHERE id = :id")
    suspend fun deleteMistake(id: Long)

    // ==========================================
    // 9. Weak Topics
    // ==========================================
    @Query("SELECT * FROM weak_topics ORDER BY (mistakeCount - correctCount) DESC")
    fun getWeakTopicsFlow(): Flow<List<WeakTopicEntity>>

    @Query("SELECT * FROM weak_topics ORDER BY (mistakeCount - correctCount) DESC")
    suspend fun getWeakTopicsList(): List<WeakTopicEntity>

    @Query("SELECT * FROM weak_topics WHERE subject = :subject ORDER BY (mistakeCount - correctCount) DESC")
    suspend fun getWeakTopicsForSubject(subject: String): List<WeakTopicEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateWeakTopic(topic: WeakTopicEntity)

    @Query("SELECT * FROM weak_topics WHERE topic = :topic LIMIT 1")
    suspend fun getWeakTopicByName(topic: String): WeakTopicEntity?

    // ==========================================
    // 10. Evaluation Records
    // ==========================================
    @Query("SELECT * FROM evaluation_records ORDER BY timestamp DESC")
    fun getAllEvaluations(): Flow<List<EvaluationRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvaluation(evaluation: EvaluationRecordEntity): Long
}
