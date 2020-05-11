package com.example.taskapp.receivers.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.taskapp.MyApp.Companion.TASK_KEY
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.database.entities.toTaskMinimal
import com.example.taskapp.fragments.PickCustomNotificationDelayFragment.Companion.DELAY_VALUE_KEY
import com.example.taskapp.repos.task.TaskRepository.Companion.ERROR_TASK
import com.example.taskapp.utils.alarmCreator.AlarmCreator
import com.example.taskapp.utils.alarmCreator.AlarmCreatorImpl
import com.example.taskapp.utils.notification.NotificationIntentFactoryImpl
import com.example.taskapp.utils.notification.NotificationManagerHelper

class DelayNotificationReceiver :
    BroadcastReceiver() {

    private lateinit var alarmCreator : AlarmCreator

    override fun onReceive(context: Context, intent: Intent) {
        alarmCreator = AlarmCreatorImpl(context,NotificationIntentFactoryImpl(context))

        NotificationManagerHelper.cancelTaskNotification(context)

        val task: TaskMinimal =
            intent.getParcelableExtra(TASK_KEY) ?: ERROR_TASK.toTaskMinimal()
        val time = intent.getIntExtra(DELAY_VALUE_KEY, 30)
        alarmCreator.setDelayAlarm(task, time.toLong())
    }

    companion object {
        const val DELAY_NOTIFICATION_ACTION = "delay notif action"

    }
}
