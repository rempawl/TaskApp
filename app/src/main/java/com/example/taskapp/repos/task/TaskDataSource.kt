package com.example.taskapp.repos.task

import com.example.taskapp.database.Result
import com.example.taskapp.database.entities.Task
import com.example.taskapp.database.entities.TaskMinimal
import io.reactivex.Single
import org.threeten.bp.LocalDate

interface TaskDataSource {
    suspend fun saveTask(task: Task)
            : Single<Long>

    suspend fun getMinTasksByUpdateDate(date: LocalDate): Result<List<TaskMinimal>>

    suspend fun getTasksUntilDate(date: LocalDate) : Result<List<Task>>

    suspend fun getTasksByUpdateDate(date: LocalDate): Result<List<Task>>

    suspend fun deleteTask(id: Long): Int

    suspend fun getTasks(): Result<List<Task>>

    suspend fun getMinimalTasks(): Result<List<TaskMinimal>>

    suspend fun getTaskById(id: Long): Result<Task>

    suspend fun updateTask(task: Task): Int
    suspend fun updateTasks(tasks: List<Task>): Int
    suspend fun getNotTodayTasks(): Result<List<Task>>
}