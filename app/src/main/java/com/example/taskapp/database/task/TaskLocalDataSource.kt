package com.example.taskapp.database.task

import javax.inject.Inject

class TaskLocalDataSource    @Inject constructor (private val taskDao: TaskDao) {

    fun saveTask(task: Task) { taskDao.insertItem(task)}
}