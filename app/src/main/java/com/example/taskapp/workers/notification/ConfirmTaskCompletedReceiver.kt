package com.example.taskapp.workers.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.taskapp.utils.notification.TaskNotificationManager

class ConfirmTaskCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        TaskNotificationManager.cancelNotification(context)
        //todo
    }
}
