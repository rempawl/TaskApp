package com.example.taskapp.workers.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.fragments.PickCustomNotificationDelayFragment.Companion.DELAY_VALUE_KEY
import com.example.taskapp.repos.task.TaskRepository.Companion.ERROR_TASK
import com.example.taskapp.workers.AlarmCreator
import com.example.taskapp.workers.notification.CreateNotificationBroadcastReceiver.Companion.TASK_KEY
import com.example.taskapp.workers.toTaskMinimal
import javax.inject.Inject

class DelayNotificationReceiver @Inject constructor(private val alarmCreator: AlarmCreator) :
    BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val task: TaskMinimal =
            intent.getParcelableExtra<TaskMinimal>(TASK_KEY) ?: ERROR_TASK.toTaskMinimal()
        val time = intent.getIntExtra(DELAY_VALUE_KEY, 30)
        alarmCreator.setDelayAlarm(task, time.toLong())
    }

    companion object {
        const val DELAY_NOTIFICATION_ACTION = "delay notif action"

    }
}
