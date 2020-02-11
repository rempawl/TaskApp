package com.example.taskapp.di

import android.content.Context
import androidx.room.Room
import com.example.taskapp.database.AppDataBase
import com.example.taskapp.database.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class DataBaseModule {

    @Reusable
    @Provides
    fun provideDataBase(context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, AppDataBase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Reusable
    @Provides
    fun provideTaskDao(appDataBase: AppDataBase): TaskDao = appDataBase.taskDao()


}