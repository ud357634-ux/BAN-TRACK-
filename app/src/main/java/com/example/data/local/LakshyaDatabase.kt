package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        SebaSubjectEntity::class,
        StudyModuleEntity::class,
        SpacedRepetitionMetricEntity::class,
        ModuleReviewLogEntity::class,
        SubjectProgressMetricEntity::class,
        TestSessionEntity::class,
        MistakeEntity::class,
        WeakTopicEntity::class,
        EvaluationRecordEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class LakshyaDatabase : RoomDatabase() {
    abstract fun lakshyaDao(): LakshyaDao

    companion object {
        @Volatile
        private var INSTANCE: LakshyaDatabase? = null

        fun getInstance(context: Context): LakshyaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LakshyaDatabase::class.java,
                    "lakshya_seba_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Populate default SEBA Class 10 curriculum and spaced repetition metrics
                            CoroutineScope(Dispatchers.IO).launch {
                                INSTANCE?.let { database ->
                                    val dao = database.lakshyaDao()
                                    dao.insertSubjects(SebaCurriculumDefaults.DEFAULT_SUBJECTS)
                                    dao.insertModules(SebaCurriculumDefaults.DEFAULT_MODULES)
                                    dao.insertMetrics(SebaCurriculumDefaults.createInitialSpacedMetrics())
                                    SebaCurriculumDefaults.createInitialSubjectProgress().forEach {
                                        dao.insertOrUpdateSubjectProgress(it)
                                    }
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
