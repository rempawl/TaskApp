package com.example.taskapp.repos.task

import androidx.lifecycle.LiveData
import com.example.taskapp.database.Result
import com.example.taskapp.database.entities.task.DefaultTask
import com.example.taskapp.database.entities.task.TaskMinimal
import io.reactivex.Single
import org.threeten.bp.LocalDate

interface TaskRepositoryInterface {
    suspend fun getTasks(): Result<List<DefaultTask>>

    suspend fun deleteByID(id: Long): Int

    suspend fun saveTask(task: DefaultTask): Long

    suspend fun getMinTasksByUpdateDate(date: LocalDate = LocalDate.now()): List<TaskMinimal>

    suspend fun getTasksByUpdateDate(date: LocalDate = LocalDate.now()): List<DefaultTask>

    suspend fun getTaskByID(id: Long): DefaultTask

    suspend fun getMinimalTasks(): LiveData<List<TaskMinimal>>

    suspend fun getTasksUntilDate(date: LocalDate = LocalDate.now()): List<DefaultTask>

    suspend fun updateTask(task: DefaultTask): Int
    suspend fun updateTasks(tasks: List<DefaultTask>): Int

    suspend fun getTodayMinTasks(): LiveData<List<TaskMinimal>>
    suspend fun getNotTodayTasks(): List<DefaultTask>
}