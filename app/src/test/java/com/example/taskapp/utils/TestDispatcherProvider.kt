package com.example.taskapp.utils

import com.example.taskapp.utils.providers.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider :
    DispatcherProvider {
     val dispatcher = TestCoroutineDispatcher()

    override fun provideIoDispatcher(): TestCoroutineDispatcher = dispatcher

    override fun provideMainDispatcher(): TestCoroutineDispatcher = dispatcher

    override fun provideDefaultDispatcher(): TestCoroutineDispatcher = dispatcher

}