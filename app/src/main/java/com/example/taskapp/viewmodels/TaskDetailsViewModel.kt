package com.example.taskapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.taskapp.database.entities.task.DefaultTask
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.dispatcherProvider.DispatcherProvider
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class TaskDetailsViewModel @AssistedInject constructor(
    @Assisted private val taskID: Long,
    private val taskRepository: TaskRepositoryInterface,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {


    suspend fun deleteTask(): Int {
        return taskRepository.deleteByID(taskID)
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(taskID: Long): TaskDetailsViewModel
    }

    val task: LiveData<DefaultTask> = liveData(dispatcherProvider.provideIoDispatcher()) {
        val data = taskRepository.getTaskByID(taskID)
        emit(data)
    }


}
