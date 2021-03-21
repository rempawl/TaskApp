package com.example.taskapp.broadcastReceivers.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.taskapp.MyApp.Companion.TASK_KEY
import com.example.taskapp.data.task.TaskMinimal
import com.example.taskapp.fragments.PickCustomNotificationDelayFragment.Companion.DELAY_VALUE_KEY
import com.example.taskapp.repos.task.TaskRepository.Companion.ERROR_TASK
import com.example.taskapp.utils.alarmCreator.AlarmCreator
import com.example.taskapp.utils.alarmCreator.AlarmCreatorImpl
import com.example.taskapp.utils.notification.NotificationIntentFactoryImpl
import com.example.taskapp.utils.notification.NotificationManagerHelper
import com.example.taskapp.utils.notification.NotificationManagerHelperImpl

class DelayNotificationReceiver : BroadcastReceiver() {

    //todo Inject
    private lateinit var alarmCreator: AlarmCreator
    private lateinit var notificationManagerHelper: NotificationManagerHelper

    override fun onReceive(context: Context, intent: Intent) {
        alarmCreator = AlarmCreatorImpl(context, NotificationIntentFactoryImpl(context))
        notificationManagerHelper = NotificationManagerHelperImpl(context)

        notificationManagerHelper.cancelTaskNotification()

        val task: TaskMinimal =
            intent.getParcelableExtra(TASK_KEY) ?: ERROR_TASK.toTaskMinimal()

        val time = intent.getIntExtra(DELAY_VALUE_KEY, 30).toLong()
        alarmCreator.setDelayAlarm(task, time)
    }

    companion object {
        const val DELAY_NOTIFICATION_ACTION = "delay notif action"
    }
}
