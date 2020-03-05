package com.example.taskapp.di

import com.example.taskapp.repos.streak.StreakDataSource
import com.example.taskapp.repos.streak.StreakLocalDataSource
import com.example.taskapp.repos.task.TaskRepository
import com.example.taskapp.repos.task.TaskRepositoryInterface
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideTaskRepository(taskRepository: TaskRepository) : TaskRepositoryInterface

    @Binds
    abstract fun provideStreakDataSource(streakDataSource: StreakLocalDataSource): StreakDataSource
}