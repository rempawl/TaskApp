package com.example.taskapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.taskapp.database.dao.TaskDao
import com.example.taskapp.repos.task.TaskRepository
import javax.inject.Inject

class MyTasksViewModel  @Inject constructor(private  val taskRepo: TaskRepository): ViewModel() {

    @Volatile
    private var tasks = liveData{
        val data = taskRepo.getTasks()
        emit(data)
    }
}
