package com.example.taskapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.taskapp.database.Result
import com.example.taskapp.database.entities.Task
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.repos.task.TaskRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class TaskDetailsViewModel @AssistedInject constructor(
    @Assisted val taskMinimal: TaskMinimal,
    private val taskRepository: TaskRepository
) : ViewModel() {
    @AssistedInject.Factory
    interface Factory {
        fun create(taskMinimal: TaskMinimal): TaskDetailsViewModel
    }

    val task: LiveData<Task> = liveData {
        val data = taskRepository.getTaskByID(taskMinimal.taskID) ?: Task(name="error")
        emit(data)
    }


}
