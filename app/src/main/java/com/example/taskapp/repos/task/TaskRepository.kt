package com.example.taskapp.repos.task

import com.example.taskapp.data.Result
import com.example.taskapp.data.task.Task
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

interface TaskRepository {
    suspend fun getTasks(): Flow<Result<*>>

    suspend fun deleteByID(id: Long): Int

    suspend fun getMinTasksByUpdateDate(date: LocalDate = LocalDate.now()): Flow<Result<*>>

    suspend fun getTasksByUpdateDate(date: LocalDate = LocalDate.now()): Flow<Result<*>>

    suspend fun getTaskByID(id: Long): Flow<Result<*>>

    suspend fun getMinimalTasks(): Flow<Result<*>>

    suspend fun getTasksUntilDate(date: LocalDate = LocalDate.now()): Flow<Result<*>>

    suspend fun updateTask(task: Task): Int
    suspend fun updateTasks(tasks: List<Task>): Int

    suspend fun getTodayMinTasks(): Flow<Result<*>>
    suspend fun getNotTodayTasks(): Flow<Result<*>>
    suspend fun saveTask(task: Task)
}