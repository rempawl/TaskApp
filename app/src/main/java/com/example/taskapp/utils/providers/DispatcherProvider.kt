package com.example.taskapp.utils.providers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val ioDispatcher : CoroutineDispatcher
    val mainDispatcher : CoroutineDispatcher
    val defaultDispatcher : CoroutineDispatcher
}