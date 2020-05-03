package com.example.taskapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.taskapp.repos.task.TaskRepository
import javax.inject.Inject

class TodayViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {


    val tasks = liveData {
        val data = taskRepository.getTodayMinTasks()
        emitSource(data)
    }


}
