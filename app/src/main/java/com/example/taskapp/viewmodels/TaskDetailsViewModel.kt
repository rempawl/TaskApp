package com.example.taskapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.taskapp.MainActivity
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.repos.task.TaskRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.MainScope

class TaskDetailsViewModel @AssistedInject constructor(
    @Assisted val taskMinimal: TaskMinimal,
    private val taskRepository: TaskRepository
) : ViewModel() {
    @AssistedInject.Factory
    interface Factory {
        fun create(taskMinimal: TaskMinimal): TaskDetailsViewModel
    }

    val task = liveData {
        val data =taskRepository.getTaskByID(taskMinimal.taskID)
        emit(data)
    }

}
