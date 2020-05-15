package com.example.taskapp.utils.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import com.example.taskapp.MyApp
import javax.inject.Inject

class NotificationManagerHelperImpl @Inject constructor(private val context: Context) :
    NotificationManagerHelper {
    private fun getNotificationManager() =
        NotificationManagerCompat.from(context)

    override fun cancelTaskNotification() {
        getNotificationManager().cancel(MyApp.TASK_NOTIFICATION_ID)
    }

    override fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    MyApp.TASK_CHANNEL_ID,
                    "task channel",
                    NotificationManager.IMPORTANCE_HIGH
                )
                    .apply { description = "" }

            getNotificationManager().createNotificationChannel(channel)
        }
    }

}