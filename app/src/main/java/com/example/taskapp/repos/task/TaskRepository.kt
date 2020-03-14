package com.example.taskapp.repos.task

import com.example.taskapp.database.Result
import com.example.taskapp.database.entities.Task
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.workers.toTaskMinimal
import dagger.Reusable
import org.threeten.bp.LocalDate
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
@Reusable
class TaskRepository @Inject constructor(private val taskLocalDataSource: TaskLocalDataSource) :
    TaskRepositoryInterface {

    override suspend fun getTasks(): List<Task> {
        val data = taskLocalDataSource.getTasks()
        return if (data is Result.Success<*>) {
            data.items as List<Task>
        } else {
            listOf(ERROR_TASK)
        }
    }

    override suspend fun deleteByID(id: Long) = taskLocalDataSource.deleteTask(id)

    override suspend fun saveTask(task: Task) = taskLocalDataSource.saveTask(task)


    override suspend fun getMinTasksByUpdateDate(date: LocalDate): List<TaskMinimal> {
        val result = taskLocalDataSource.getMinTasksByUpdateDate(date)
        return if (result is Result.Success<*>) {
            result.items as List<TaskMinimal>
        } else {
            listOf(ERROR_TASK.toTaskMinimal())
        }

    }
    override suspend fun getTasksByUpdateDate(date: LocalDate): List<Task> {
        val result = taskLocalDataSource.getTasksByUpdateDate(date)
        return if (result is Result.Success<*>) {
            result.items as List<Task>
        } else {
            listOf(ERROR_TASK)
        }

    }

    override suspend fun getTaskByID(id: Long): Task {
        val result = taskLocalDataSource.getTaskById(id)
        return if (result is Result.Success<*>) {
            result.items as Task
        } else {
            (ERROR_TASK)
        }
    }

    override suspend fun getMinimalTasks(): List<TaskMinimal> {
        val data = taskLocalDataSource.getMinimalTasks()
        return if (data is Result.Success<*>) {
            data.items as List<TaskMinimal>
        } else {
            emptyList()
        }
    }

    override suspend fun getTasksUntilDate(date: LocalDate): List<Task> {
        val data = taskLocalDataSource.getTasksUntilDate(date)
        return if(data is Result.Success<*>){
            data.items as List<Task>
        }else{
            listOf(ERROR_TASK)
        }

    }

    override suspend fun updateTask(task: Task) = taskLocalDataSource.updateTask(task)

    override suspend fun updateTasks(tasks : List<Task>) = taskLocalDataSource.updateTasks(tasks)

    companion object{
        val ERROR_TASK = Task(taskID = -1,name="ERROR")
    }
}