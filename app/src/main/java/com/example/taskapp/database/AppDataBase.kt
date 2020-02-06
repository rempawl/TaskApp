package com.example.taskapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskapp.database.entities.Task
import com.example.taskapp.database.dao.TaskDao

@Database(
    entities = [Task::class],
    version = AppDataBase.VERSION_INT
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        const val VERSION_INT = 2
        private const val DB_NAME = "TaskApp DB"

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