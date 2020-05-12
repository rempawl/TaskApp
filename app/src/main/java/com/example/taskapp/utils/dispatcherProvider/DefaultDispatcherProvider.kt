package com.example.taskapp.utils.dispatcherProvider

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DefaultDispatcherProvider @Inject constructor() :
    DispatcherProvider {
    override fun provideIoDispatcher(): CoroutineDispatcher =
        Dispatchers.IO

    override fun provideMainDispatcher(): CoroutineDispatcher =
        Dispatchers.Main

    override fun provideDefaultDispatcher(): CoroutineDispatcher =
        Dispatchers.Default

}