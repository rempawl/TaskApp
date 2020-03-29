package com.example.taskapp.utils.notification

import android.content.Context
import android.content.Intent
import com.example.taskapp.MyApp.Companion.CREATE_NOTIFICATION_ACTION
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.workers.notification.CreateTaskNotificationBroadcastReceiver
import com.example.taskapp.workers.notification.DelayNotificationReceiver


object NotificationIntentFactory {

    fun createDelayNotificationIntent(context: Context, delayValue: Int, task: TaskMinimal)
            : Intent {
        return Intent(context, DelayNotificationReceiver::class.java)
            .apply {
                putExtra(DELAY_VALUE_KEY, delayValue)
                putExtra(TASK_KEY, task)
            }
    }

    fun createNotificationReceiverIntent(task: TaskMinimal, context: Context): Intent {
        return Intent(context, CreateTaskNotificationBroadcastReceiver::class.java).apply {
            putExtra(TASK_NAME_KEY, task.name)
            putExtra(TASK_DESC_KEY, task.description)
            putExtra(TASK_ID_KEY, task.taskID)
            action = CREATE_NOTIFICATION_ACTION
        }

    }

    const val TASK_KEY = "task"
    const val DELAY_VALUE_KEY = "delay value"
    const val TASK_NAME_KEY = "task name"
    const val TASK_DESC_KEY = "task desc"
    const val TASK_ID_KEY = "task id"
    const val TASK_ID_LIST_KEY = "list of task ids"


}
