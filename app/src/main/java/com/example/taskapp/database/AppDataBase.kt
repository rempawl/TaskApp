package com.example.taskapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskapp.database.task.Task
import com.example.taskapp.database.task.TaskDao

@Database(entities = [Task::class],
    version = AppDataBase.VERSION_INT
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun taskDao() : TaskDao
    companion object {
        const val  VERSION_INT = 1
        const val DB_NAME = "Task App DB"

        private var INSTANCE : AppDataBase? = null
        fun getInstance(context: Context) : AppDataBase{
            return if(INSTANCE != null){
                INSTANCE as AppDataBase
            }else{
                INSTANCE = Room
                    .databaseBuilder(context,AppDataBase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE as AppDataBase
            }
        }
    }
}