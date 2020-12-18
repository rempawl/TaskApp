package com.example.taskapp.repos.task

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.taskapp.MainActivity.Companion.TAG
import com.example.taskapp.MyApp.Companion.TODAY
import com.example.taskapp.database.Result
import com.example.taskapp.database.dao.TaskDao
import com.example.taskapp.database.entities.task.DefaultTask
import com.example.taskapp.database.entities.task.TaskMinimal
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import javax.inject.Inject

@Reusable
class TaskLocalDataSource @Inject constructor(private val taskDao: TaskDao) : TaskDataSource {

    override suspend fun saveTask(task: DefaultTask) = withContext(Dispatchers.IO) {
        Log.d(TAG,"$task, datasrc")
        taskDao.insertTask(task)
    }

    override suspend fun getMinTasksByUpdateDate(date: LocalDate) : Result<LiveData<List<TaskMinimal>>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(taskDao.getMinTasksByRealizationDate(date))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTasksUntilDate(date: LocalDate): Result<List<DefaultTask>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Result.Success(taskDao.getTaskWithRealizationDateUntilDate(date))
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getTasksByUpdateDate(date: LocalDate) = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(taskDao.getTasksByRealizationDate(date))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }


    override suspend fun getNotTodayTasks() = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(taskDao.getTasksWithRealizationDateDifferentThanDate(TODAY))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteTask(id: Long) = withContext(Dispatchers.IO) {
        taskDao.deleteByID(id)
    }

    override suspend fun getTasks() = withContext(Dispatchers.IO){
        try{
            Result.Success(taskDao.getAllTasks())
        }catch (exception: Exception){
            Result.Error(exception)
        }

    }

    override suspend fun getMinimalTasks() = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(taskDao.getMinimalTasks())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTaskById(id: Long) = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(taskDao.getTaskById(id))
        } catch (e: Exception) {
            Result.Error(e)
        }

    }

    override suspend fun updateTask(task: DefaultTask) = withContext(Dispatchers.IO) {
        taskDao.updateItem(task)
    }

    override suspend fun updateTasks(tasks: List<DefaultTask>) = withContext(Dispatchers.IO) {
        taskDao.updateItems(tasks)
    }


}
