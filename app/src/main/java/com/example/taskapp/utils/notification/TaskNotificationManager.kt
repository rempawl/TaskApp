package com.example.taskapp.utils.notification

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.example.taskapp.workers.notification.CreateNotificationBroadcastReceiver
import javax.inject.Inject

class TaskNotificationManager @Inject constructor(){
    companion object{
        fun cancelNotification(context: Context){
            NotificationManagerCompat.from(context).cancel(CreateNotificationBroadcastReceiver.TASK_NOTIFICATION_ID)
        }
    }
}
