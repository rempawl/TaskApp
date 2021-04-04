package com.example.taskapp.utils

import com.example.taskapp.utils.providers.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider :
    DispatcherProvider {
    val test = TestCoroutineDispatcher()

    override val ioDispatcher: TestCoroutineDispatcher = test

    override val mainDispatcher: TestCoroutineDispatcher = test

    override val defaultDispatcher: TestCoroutineDispatcher = test

}