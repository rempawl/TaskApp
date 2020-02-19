package com.example.taskapp.repos.task

import com.example.taskapp.database.Result
import com.example.taskapp.database.dao.TaskDao
import com.example.taskapp.database.entities.Task
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import javax.inject.Inject

@Reusable
class TaskLocalDataSource @Inject constructor(private val taskDao: TaskDao) : TaskDataSource {

    override suspend fun saveTask(task: Task) = withContext(Dispatchers.IO) {
        taskDao.insertItem(task)
    }

    override suspend fun getMinTasksByUpdateDate(date: LocalDate) = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(taskDao.loadMinTasksByUpdateDate(date))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    override suspend fun getTasksByUpdateDate(date: LocalDate) = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(taskDao.loadTasksByUpdateDate(date))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }




    override suspend fun deleteTask(id: Long) = withContext(Dispatchers.IO) {
        taskDao.deleteByID(id)
    }

    override suspend fun getTasks() = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(taskDao.loadAllTasks())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getMinimalTasks() = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(taskDao.loadMinimalTasks())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTaskById(id: Long) = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(taskDao.loadTaskById(id))
        } catch (e: Exception) {
            Result.Error(e)
        }

    }

    override suspend fun updateTask(task: Task) = withContext(Dispatchers.IO){
        taskDao.updateItem(task)
    }


}
