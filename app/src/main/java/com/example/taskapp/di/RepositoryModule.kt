package com.example.taskapp.di

import com.example.taskapp.repos.streak.StreakDataSource
import com.example.taskapp.repos.streak.StreakLocalDataSource
import com.example.taskapp.repos.task.TaskRepositoryImpl
import com.example.taskapp.repos.task.TaskRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideTaskRepository(taskRepositoryImpl: TaskRepositoryImpl) : TaskRepository

    @Binds
    abstract fun provideStreakDataSource(streakDataSource: StreakLocalDataSource): StreakDataSource
}