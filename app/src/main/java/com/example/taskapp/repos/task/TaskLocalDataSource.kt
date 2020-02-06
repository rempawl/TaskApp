package com.example.taskapp.repos.task

import com.example.taskapp.database.dao.TaskDao
import com.example.taskapp.database.entities.Task
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Reusable
class TaskLocalDataSource @Inject constructor(private val taskDao: TaskDao) {

    suspend fun saveTask(task: Task) = withContext(Dispatchers.IO){
        taskDao.insertItem(task)
    }

    suspend fun getTasks() = withContext(Dispatchers.IO) {
        taskDao.loadAllTasks()
    }

    suspend fun getTaskById(id: Long) = withContext(Dispatchers.IO) {
        taskDao.loadTaskById(id)
    }


}
