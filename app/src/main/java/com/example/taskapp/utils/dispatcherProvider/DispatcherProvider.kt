package com.example.taskapp.utils.dispatcherProvider

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    fun provideIoDispatcher() : CoroutineDispatcher
    fun provideMainDispatcher() : CoroutineDispatcher
    fun provideDefaultDispatcher() : CoroutineDispatcher
}