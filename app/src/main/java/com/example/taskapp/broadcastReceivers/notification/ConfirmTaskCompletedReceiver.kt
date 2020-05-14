package com.example.taskapp.broadcastReceivers.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.taskapp.utils.notification.NotificationManagerHelper

class ConfirmTaskCompletedReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        NotificationManagerHelper.cancelTaskNotification(context)
        //todo update stats
    }
}
