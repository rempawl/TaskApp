package com.example.taskapp.di

import android.content.Context
import androidx.room.Room
import com.example.taskapp.database.AppDataBase
import com.example.taskapp.database.task.Task
import com.example.taskapp.database.task.TaskDao
import dagger.*
import javax.inject.Inject
import javax.inject.Singleton

@Module
 class DataBaseModule ()  {

    @Provides
    fun provideDataBase(context: Context) : AppDataBase{
        return AppDataBase.getInstance(context)
    }

    @Provides
    fun provideTaskDao(appDataBase: AppDataBase) : TaskDao = appDataBase.taskDao()


}