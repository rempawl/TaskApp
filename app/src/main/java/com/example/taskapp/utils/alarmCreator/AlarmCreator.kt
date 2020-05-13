package com.example.taskapp.utils.alarmCreator

import com.example.taskapp.database.entities.task.DefaultTask
import com.example.taskapp.database.entities.task.TaskMinimal

interface AlarmCreator {
    fun setTaskNotificationAlarm(task: DefaultTask, isToday: Boolean = false)
    fun setUpdateTaskListAlarm()
    fun setDelayAlarm(task: TaskMinimal, interval: Long = 30)

}