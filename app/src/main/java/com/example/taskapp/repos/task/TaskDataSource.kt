package com.example.taskapp.repos.task

import androidx.lifecycle.LiveData
import com.example.taskapp.data.Result
import com.example.taskapp.database.entities.task.DefaultTask
import com.example.taskapp.database.entities.task.TaskMinimal
import org.threeten.bp.LocalDate

interface TaskDataSource {
    suspend fun saveTask(task: DefaultTask)
            : Long

    suspend fun getMinTasksByUpdateDate(date: LocalDate): Result<LiveData<List<TaskMinimal>>>

    suspend fun getTasksUntilDate(date: LocalDate): Result<List<DefaultTask>>

    suspend fun getTasksByUpdateDate(date: LocalDate): Result<LiveData<List<DefaultTask>>>

    suspend fun deleteTask(id: Long): Int

    suspend fun getTasks(): Result<List<DefaultTask>>

    suspend fun getMinimalTasks(): Result<LiveData<List<TaskMinimal>>>

    suspend fun getTaskById(id: Long): Result<DefaultTask>

    suspend fun updateTask(task: DefaultTask): Int

    suspend fun updateTasks(tasks: List<DefaultTask>): Int
    suspend fun getNotTodayTasks(): Result<List<DefaultTask>>
}