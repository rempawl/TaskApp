package com.example.taskapp.utils.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import com.example.taskapp.MyApp
import com.example.taskapp.MyApp.Companion.TASK_NOTIFICATION_ID


//todo
object NotificationManagerHelper {

    private fun getNotificationManager(context: Context) = NotificationManagerCompat.from(context)

    fun cancelTaskNotification(context: Context) {
        getNotificationManager(context).cancel(TASK_NOTIFICATION_ID)
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    MyApp.TASK_CHANNEL_ID,
                    "task channel",
                    NotificationManager.IMPORTANCE_HIGH
                )
                    .apply { description = "" }

            getNotificationManager(context).createNotificationChannel(channel)
        }
    }


}
