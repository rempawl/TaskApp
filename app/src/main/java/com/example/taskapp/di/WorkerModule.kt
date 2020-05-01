package com.example.taskapp.di

import com.example.taskapp.workers.UpdateRemindersWorkerInitializer
import com.example.taskapp.workers.WorkersInitializer
import dagger.Binds
import dagger.Module

@Module
abstract class WorkerModule {

    @Binds
    abstract fun provideWorkersInitializer(
        workersInitializer: UpdateRemindersWorkerInitializer
    ): WorkersInitializer
}