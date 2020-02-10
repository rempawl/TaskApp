package com.example.taskapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.taskapp.database.Result
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.repos.task.TaskRepository
import javax.inject.Inject

class MyTasksViewModel @Inject constructor(private val taskRepo: TaskRepository) : ViewModel() {

    @Volatile
    var tasks = liveData {
        val tasks = taskRepo.getMinimalTasks()
        emit(tasks)
    }
}
