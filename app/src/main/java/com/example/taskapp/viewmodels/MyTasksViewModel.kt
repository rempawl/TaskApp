package com.example.taskapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.taskapp.data.Result
import com.example.taskapp.repos.task.TaskRepository
import com.example.taskapp.utils.providers.DispatcherProvider
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class MyTasksViewModel @Inject constructor(private val taskRepo: TaskRepository,
                                           private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val result : LiveData<Result<*>> = liveData(dispatcherProvider.ioDispatcher) {
        val tasks = taskRepo.getMinimalTasks().asLiveData(coroutineContext)
        emitSource(tasks)
    }

}
