package com.example.taskapp.workers

import com.example.taskapp.data.Result

abstract class TaskUpdater {
    abstract suspend fun updateTasksAndSetNotifications(): Result<*>

}