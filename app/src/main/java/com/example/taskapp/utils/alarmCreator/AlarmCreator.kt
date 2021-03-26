package com.example.taskapp.utils.alarmCreator

import com.example.taskapp.data.task.Task
import com.example.taskapp.data.task.TaskMinimal

interface AlarmCreator {
    fun setTaskNotificationAlarm(task: Task, isToday: Boolean = false)
    fun setDelayAlarm(task: TaskMinimal, interval: Long = 30)
}