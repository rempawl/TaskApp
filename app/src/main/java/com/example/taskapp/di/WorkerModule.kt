package com.example.taskapp.di

import com.example.taskapp.workers.NotificationAndTaskWorkersInitializer
import com.example.taskapp.workers.WorkersInitializer
import dagger.Binds
import dagger.Module

@Module
abstract class WorkerModule {
    @Binds
    abstract fun provideNotificationsAndTaskWorkersCreator(
        workersInitializer: NotificationAndTaskWorkersInitializer
    ): WorkersInitializer
}