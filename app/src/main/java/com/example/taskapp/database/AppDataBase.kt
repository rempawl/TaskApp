package com.example.taskapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskapp.database.dao.StreakDao
import com.example.taskapp.database.dao.TaskDao
import com.example.taskapp.database.entities.Streak
import com.example.taskapp.database.entities.Task
import com.example.taskapp.utils.Converters

@Database(
    entities = [Task::class,
    Streak::class


    ],
    version = AppDataBase.VERSION_INT
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    abstract fun streakDao() : StreakDao
    companion object {
        const val VERSION_INT = 19
        const val DB_NAME = "TaskApp DB"
        val INITIAL_TASKS = listOf<Task>()


        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, AppDataBase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE as AppDataBase
            }
        }
    }
}
