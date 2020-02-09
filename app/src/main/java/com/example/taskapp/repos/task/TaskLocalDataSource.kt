package com.example.taskapp.repos.task

import com.example.taskapp.database.Result
import com.example.taskapp.database.dao.TaskDao
import com.example.taskapp.database.entities.Task
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Reusable
class TaskLocalDataSource @Inject constructor(private val taskDao: TaskDao) {

    suspend fun saveTask(task: Task) = withContext(Dispatchers.IO) {
        taskDao.insertItem(task)
    }

    suspend fun getTasks() = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(taskDao.loadAllTasks())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getMinimalTasks() = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(taskDao.loadMinimalTasks())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getTaskById(id: Long) = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(taskDao.loadTaskById(id))
        } catch (e: Exception) {
            Result.Error(e)
        }

    }


}
