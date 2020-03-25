package com.example.taskapp.repos.task

import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.database.entities.TaskMinimal
import io.reactivex.Single
import org.threeten.bp.LocalDate

interface TaskRepositoryInterface {
    suspend fun getTasks(): List<DefaultTask>

    suspend fun deleteByID(id: Long): Int

    suspend fun saveTask(task: DefaultTask)
            : Single<Long>

    suspend fun getMinTasksByUpdateDate(date: LocalDate = LocalDate.now()): List<TaskMinimal>

    suspend fun getTasksByUpdateDate(date: LocalDate = LocalDate.now()): List<DefaultTask>

    suspend fun getTaskByID(id: Long): DefaultTask

    suspend fun getMinimalTasks(): List<TaskMinimal>

    suspend fun getTasksUntilDate(date: LocalDate = LocalDate.now()): List<DefaultTask>

    suspend fun updateTask(task: DefaultTask): Int
    suspend fun updateTasks(tasks: List<DefaultTask>): Int
    suspend fun getTodayMinTasks(): List<TaskMinimal>
    suspend fun getNotTodayTasks(): List<DefaultTask>
}