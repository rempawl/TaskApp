package com.example.taskapp.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.taskapp.MainActivity
import com.example.taskapp.R
import com.example.taskapp.database.entities.Task

class NotificationBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            if (context != null) {
                val task: Task = intent.getParcelableExtra("task")
                    ?: throw IllegalStateException("task passed from alarm is null")

                createNotificationChannel(context)
                createNotification(context,task)
            }
        }
    }

    private fun createNotification(context: Context,task: Task) {

            val intent =
                Intent(context, MainActivity::class.java).putParcelableArrayListExtra(
                    "task",arrayListOf(task)
                )
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle("todo strings.xml Czas wykonac zadanie")
                .setContentText(" ${task.name}  ${task.description} ")
                .setSmallIcon(R.drawable.ic_alarm_on_black_24dp)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .build()
            NotificationManagerCompat.from(context).notify(0,notification)

    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "task channel", NotificationManager.IMPORTANCE_HIGH)
                .apply { description = "todo string.xml powiadomienia nadchodzacych zadan" }
            NotificationManagerCompat.from(context).createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "pending task Notifications channel"
        const val TASK_NOTIFICATION_ID = 0
    }
}