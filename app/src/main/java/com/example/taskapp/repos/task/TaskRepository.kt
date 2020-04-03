package com.example.taskapp.repos.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskapp.database.Result
import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.workers.toTaskMinimal
import dagger.Reusable
import org.threeten.bp.LocalDate
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
@Reusable
class TaskRepository @Inject constructor(private val taskLocalDataSource: TaskLocalDataSource) :
    TaskRepositoryInterface {

    override suspend fun getTasks(): List<DefaultTask> {
        val data = taskLocalDataSource.getTasks()
        return if (data is Result.Success<*>) {
            data.items as List<DefaultTask>
        } else {
            listOf(ERROR_TASK)
        }
    }

    override suspend fun deleteByID(id: Long) = taskLocalDataSource.deleteTask(id)

    override suspend fun saveTask(task: DefaultTask) = taskLocalDataSource.saveTask(task)

    override suspend fun getNotTodayTasks(): List<DefaultTask> {
        val data = taskLocalDataSource.getNotTodayTasks()
        return if (data is Result.Success<*>) {
            data.items as List<DefaultTask>
        } else {
            listOf(ERROR_TASK)
        }
    }

    override suspend fun getMinTasksByUpdateDate(date: LocalDate): List<TaskMinimal> {
        val result = taskLocalDataSource.getMinTasksByUpdateDate(date)
        return if (result is Result.Success<*>) {
            result.items as List<TaskMinimal>
        } else {
            listOf(ERROR_TASK.toTaskMinimal())
        }

    }

    override suspend fun getTodayMinTasks(): List<TaskMinimal> {
        val result = taskLocalDataSource.getMinTasksByUpdateDate(LocalDate.now())
        return if (result is Result.Success<*>) {
            result.items as List<TaskMinimal>
        } else {
            listOf(ERROR_TASK.toTaskMinimal())
        }

    }

    override suspend fun getTasksByUpdateDate(date: LocalDate): List<DefaultTask> {
        val result = taskLocalDataSource.getTasksByUpdateDate(date)
        return if (result is Result.Success<*>) {
            result.items as List<DefaultTask>
        } else {
            listOf(ERROR_TASK)
        }

    }

    override suspend fun getTaskByID(id: Long): DefaultTask {
        val result = taskLocalDataSource.getTaskById(id)
        return if (result is Result.Success<*>) {
            result.items as DefaultTask
        } else {
            (ERROR_TASK)
        }
    }

    override suspend fun getMinimalTasks(): LiveData<List<TaskMinimal>> {
        val data = taskLocalDataSource.getMinimalTasks()
        return if (data is Result.Success<*>) {
            data.items as LiveData<List<TaskMinimal>>
        } else {
            MutableLiveData<List<TaskMinimal>>(emptyList())
        }
    }

    override suspend fun getTasksUntilDate(date: LocalDate): List<DefaultTask> {
        val data = taskLocalDataSource.getTasksUntilDate(date)
        return if (data is Result.Success<*>) {
            data.items as List<DefaultTask>
        } else {
            listOf(ERROR_TASK)
        }

    }

    override suspend fun updateTask(task: DefaultTask) = taskLocalDataSource.updateTask(task)

    override suspend fun updateTasks(tasks: List<DefaultTask>) = taskLocalDataSource.updateTasks(tasks)

    companion object {
        val ERROR_TASK = DefaultTask(taskID = -1, name = "ERROR")
    }
}
