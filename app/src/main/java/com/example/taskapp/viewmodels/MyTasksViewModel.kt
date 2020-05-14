package com.example.taskapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.providers.DispatcherProvider
import javax.inject.Inject

class MyTasksViewModel @Inject constructor(private val taskRepo: TaskRepositoryInterface,
                                           private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val tasks = liveData(dispatcherProvider.provideIoDispatcher()) {
        val tasks = taskRepo.getMinimalTasks()
        emitSource(tasks)
    }

}
