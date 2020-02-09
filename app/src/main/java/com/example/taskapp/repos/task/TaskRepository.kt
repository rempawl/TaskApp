package com.example.taskapp.repos.task

import com.example.taskapp.database.Result
import com.example.taskapp.database.entities.Task
import com.example.taskapp.database.entities.TaskMinimal
import dagger.Reusable
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
@Reusable
class TaskRepository @Inject constructor(private val taskLocalDataSource: TaskLocalDataSource) {

    suspend fun getTasks(): List<Task>? {
        val data = taskLocalDataSource.getTasks()
        return if (data is Result.Success<*>) {
            data.items as List<Task>
        } else {
            null
        }
    }

    suspend fun saveTask(task: Task) = taskLocalDataSource.saveTask(task)

    suspend fun getTaskByID(id: Long): Task? {
        val result = taskLocalDataSource.getTaskById(id)
        return if (result is Result.Success<*>) {
            result.items as Task
        } else {
            null
        }
    }

    suspend fun getMinimalTasks(): List<TaskMinimal>? {
        val data = taskLocalDataSource.getMinimalTasks()
        return if (data is Result.Success<*>) {
            data.items as List<TaskMinimal>
        } else {
            null
        }
    }

}