package com.example.taskapp.repos.task

import com.example.taskapp.MyApp.Companion.TODAY
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
        taskDao.insertTask(task)
    }

    override suspend fun getMinTasksByUpdateDate(date: LocalDate) = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(taskDao.loadMinTasksByRealizationDate(date))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTasksUntilDate(date: LocalDate): Result<List<Task>> = withContext(Dispatchers.IO){
        return@withContext try{
            Result.Success(taskDao.loadTaskWithRealizationDateUntilDate(date))
        }catch (e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun getTasksByUpdateDate(date: LocalDate) = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(taskDao.loadTasksByRealizationDate(date))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }



    override suspend fun getNotTodayTasks() = withContext(Dispatchers.IO){
        return@withContext try{
            Result.Success(taskDao.loadTasksWithRealizationDateDifferentThanDate(TODAY))
        }catch (e:Exception){
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

    override suspend fun updateTasks(tasks: List<Task>) = withContext(Dispatchers.IO){
        taskDao.updateItems(tasks)
    }


}
