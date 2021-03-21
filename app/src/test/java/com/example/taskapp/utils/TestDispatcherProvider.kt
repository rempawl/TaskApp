package com.example.taskapp.utils

import com.example.taskapp.utils.providers.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider :
    DispatcherProvider {
     val dispatcher = TestCoroutineDispatcher()

    override fun ioDispatcher(): TestCoroutineDispatcher = dispatcher

    override fun mainDispatcher(): TestCoroutineDispatcher = dispatcher

    override fun defaultDispatcher(): TestCoroutineDispatcher = dispatcher

}