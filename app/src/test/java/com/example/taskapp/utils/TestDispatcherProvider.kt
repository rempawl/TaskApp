package com.example.taskapp.utils

import com.example.taskapp.utils.dispatcherProvider.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider :
    DispatcherProvider {
    private val dispatcher = TestCoroutineDispatcher()

    override fun provideIoDispatcher(): CoroutineDispatcher = dispatcher

    override fun provideMainDispatcher(): CoroutineDispatcher = dispatcher

    override fun provideDefaultDispatcher(): CoroutineDispatcher = dispatcher

}