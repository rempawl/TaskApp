package com.example.taskapp.repos.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.taskapp.MyApp
import com.example.taskapp.database.Result
import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.database.entities.toTaskMinimal
import dagger.Reusable
import org.threeten.bp.LocalDate
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
@Reusable
class TaskRepository @Inject constructor(private val taskLocalDataSource: TaskLocalDataSource) :
    TaskRepositoryInterface {

    override suspend fun getTasks(): List<DefaultTask> {
        return when (val data = taskLocalDataSource.getTasks()) {
            is Result.Success<*> -> data.items as List<DefaultTask>
            is Result.Error -> listOf(ERROR_TASK)
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
            listOf(MIN_ERROR_TASK)
        }

    }

    @Suppress("MoveVariableDeclarationIntoWhen")
    override suspend fun getTodayMinTasks(): LiveData<List<TaskMinimal>> = liveData {
        val result = taskLocalDataSource.getMinTasksByUpdateDate(MyApp.TODAY)
        val data = when (result) {
            is Result.Success<*> -> result.items as LiveData<List<TaskMinimal>>
            is Result.Error -> MutableLiveData(listOf(MIN_ERROR_TASK))
        }
        emitSource(data)
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
        return when (val result = taskLocalDataSource.getTaskById(id)) {
            is Result.Success<*> -> result.items as DefaultTask
            is Result.Error -> (ERROR_TASK)
        }
    }

    override suspend fun getMinimalTasks(): LiveData<List<TaskMinimal>> = liveData {
        val result = taskLocalDataSource.getMinimalTasks()
        val data = if (result is Result.Success<*>) {
            result.items as LiveData<List<TaskMinimal>>
        } else {
            MutableLiveData(listOf(MIN_ERROR_TASK))
        }
        emitSource(data)

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

    override suspend fun updateTasks(tasks: List<DefaultTask>) =
        taskLocalDataSource.updateTasks(tasks)

    companion object {
        val ERROR_TASK = DefaultTask(taskID = -1, name = "An error occurred")
        val MIN_ERROR_TASK = ERROR_TASK.toTaskMinimal()

    }
}
