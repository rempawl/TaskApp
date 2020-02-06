package com.example.taskapp.repos.task

import com.example.taskapp.database.entities.Task
import com.example.taskapp.repos.task.TaskLocalDataSource
import dagger.Reusable
import javax.inject.Inject

@Reusable
class TaskRepository @Inject constructor(private val taskLocalDataSource: TaskLocalDataSource) {

    suspend fun getTasks()  = taskLocalDataSource.getTasks()

    suspend fun saveTask(task : Task)= taskLocalDataSource.saveTask(task)

}