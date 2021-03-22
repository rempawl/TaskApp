package com.example.taskapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.taskapp.data.Result
import com.example.taskapp.dataSources.task.TaskRepository
import com.example.taskapp.utils.providers.DispatcherProvider
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class TodayViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val tasks: LiveData<Result<*>> = liveData(dispatcherProvider.ioDispatcher) {
        emitSource(taskRepository.getTodayMinTasks().asLiveData(coroutineContext))
    }


}

