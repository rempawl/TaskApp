package com.example.taskapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.taskapp.database.dao.StreakDao
import com.example.taskapp.database.dao.TaskDao
import com.example.taskapp.database.entities.Streak
import com.example.taskapp.database.entities.task.DbTask
import com.example.taskapp.utils.Converters
import com.example.taskapp.utils.DbTasks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [DbTask::class,
        Streak::class
    ],
    version = AppDataBase.VERSION_INT
)

@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    abstract fun streakDao(): StreakDao

    companion object {
        const val VERSION_INT = 20
        const val DB_NAME = "TaskApp DB"


        private lateinit var INSTANCE: AppDataBase

        fun getInstance(context: Context): AppDataBase {
            val callback = createCallback(context)

            return synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room
                        .databaseBuilder(context, AppDataBase::class.java, DB_NAME)
                        .addCallback(callback)
                        .build()
                }
                INSTANCE
            }
        }

        private fun createCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        getInstance(context)
                            .taskDao()
                            .insertItems(DbTasks.tasks)
                    }
                }
            }
        }
    }
}
