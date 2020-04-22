package com.example.taskapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.taskapp.adapters.taskID
import com.example.taskapp.repos.task.TaskRepositoryInterface
import javax.inject.Inject

class AddSpontaneousTasksViewModel @Inject constructor(private val taskRepository: TaskRepositoryInterface) :
    ViewModel(){

    fun addSpontaneousTasks(checkedTasksIds: List<taskID>) {
//        task
    }

    val tasks = liveData {
        val data = taskRepository.getNotTodayTasks()
        emit(data)
    }
}
