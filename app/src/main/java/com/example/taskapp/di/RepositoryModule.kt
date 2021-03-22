package com.example.taskapp.di

import com.example.taskapp.dataSources.streak.StreakDataSource
import com.example.taskapp.dataSources.streak.StreakLocalDataSource
import com.example.taskapp.dataSources.task.TaskRepositoryImpl
import com.example.taskapp.dataSources.task.TaskRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideTaskRepository(taskRepositoryImpl: TaskRepositoryImpl) : TaskRepository

    @Binds
    abstract fun provideStreakDataSource(streakDataSource: StreakLocalDataSource): StreakDataSource
}