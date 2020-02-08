package com.example.taskapp.database

import android.content.Context
import androidx.room.*
import com.example.taskapp.database.dao.TaskDao
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.database.entities.Task
import com.example.taskapp.utils.Converters

@Database(
    entities = [Task::class

    ],
    version = AppDataBase.VERSION_INT
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        const val VERSION_INT = 10
       private const val DB_NAME = "TaskApp DB"

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return if (INSTANCE != null) {
                INSTANCE as AppDataBase
            } else {
                synchronized(this) {
                    INSTANCE = Room
                        .databaseBuilder(context, AppDataBase::class.java, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE as AppDataBase
                }
            }
        }
    }
}