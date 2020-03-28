package com.example.taskapp.workers.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.taskapp.MainActivity
import com.example.taskapp.MyApp.Companion.TASK_KEY
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.fragments.PickCustomNotificationDelayFragment.Companion.DELAY_VALUE_KEY
import com.example.taskapp.repos.task.TaskRepository.Companion.ERROR_TASK
import com.example.taskapp.utils.notification.NotificationManagerHelper
import com.example.taskapp.workers.AlarmCreator
import com.example.taskapp.workers.toTaskMinimal

class DelayNotificationReceiver :
    BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {

        NotificationManagerHelper.cancelTaskNotification(context)
        Log.d(MainActivity.TAG,"delaying")
        val task: TaskMinimal =
            intent.getParcelableExtra<TaskMinimal>(TASK_KEY) ?: ERROR_TASK.toTaskMinimal()
        val time = intent.getIntExtra(DELAY_VALUE_KEY, 30)
        AlarmCreator.setDelayAlarm(task, time.toLong(),context)
    }

    companion object {
        const val DELAY_NOTIFICATION_ACTION = "delay notif action"

    }
}
