package com.example.taskapp.utils.providers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    fun provideIoDispatcher() : CoroutineDispatcher
    fun provideMainDispatcher() : CoroutineDispatcher
    fun provideDefaultDispatcher() : CoroutineDispatcher
}