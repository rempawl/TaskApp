package com.example.taskapp.broadcastReceivers.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.taskapp.utils.notification.NotificationManagerHelper
import com.example.taskapp.utils.notification.NotificationManagerHelperImpl

class ConfirmTaskCompletedReceiver : BroadcastReceiver() {


//todo     Inject
    lateinit var notificationManagerHelper: NotificationManagerHelper

    override fun onReceive(context: Context, intent: Intent) {
        notificationManagerHelper = NotificationManagerHelperImpl(context)
        notificationManagerHelper.cancelTaskNotification()
        //todo update stats
    }
}
