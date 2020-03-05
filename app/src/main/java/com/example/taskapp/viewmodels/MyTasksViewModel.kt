package com.example.taskapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.taskapp.repos.task.TaskRepositoryInterface
import javax.inject.Inject

class MyTasksViewModel @Inject constructor(private val taskRepo: TaskRepositoryInterface) : ViewModel() {

    val tasks = liveData {
        val tasks = taskRepo.getMinimalTasks()
        emit(tasks)
    }

}
