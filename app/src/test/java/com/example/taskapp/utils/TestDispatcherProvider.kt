package com.example.taskapp.utils

import com.example.taskapp.utils.providers.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider :
    DispatcherProvider {
    val dispatcher = TestCoroutineDispatcher()

    override val ioDispatcher: TestCoroutineDispatcher = dispatcher

    override val mainDispatcher: TestCoroutineDispatcher = dispatcher

    override val defaultDispatcher: TestCoroutineDispatcher = dispatcher

}