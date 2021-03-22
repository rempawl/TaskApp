package com.example.taskapp.dataSources.task

import androidx.lifecycle.LiveData
import com.example.taskapp.data.Result
import com.example.taskapp.database.entities.task.DbTask
import com.example.taskapp.database.entities.task.DbTaskMinimal
import org.threeten.bp.LocalDate

interface TaskNetworkDataSource {
    suspend fun saveTask(task: DbTask) : Long

    suspend fun getMinTasksByUpdateDate(date: LocalDate): Result<LiveData<List<DbTaskMinimal>>>

    suspend fun getTasksUntilDate(date: LocalDate): Result<List<DbTask>>

    suspend fun getTasksByUpdateDate(date: LocalDate): Result<LiveData<List<DbTask>>>

    suspend fun deleteTask(id: Long): Int

    suspend fun getTasks(): Result<List<DbTask>>

    suspend fun getMinimalTasks(): Result<LiveData<List<DbTaskMinimal>>>

    suspend fun getTaskById(id: Long): Result<DbTask>

    suspend fun updateTask(task: DbTask): Int

    suspend fun updateTasks(tasks: List<DbTask>): Int
    suspend fun getNotTodayTasks(): Result<List<DbTask>>
}