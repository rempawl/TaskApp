package com.example.taskapp.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.taskapp.MainActivity
import com.example.taskapp.R
import com.example.taskapp.database.entities.Task

class CreateNotificationBroadcastReceiver :
    BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && context != null ) {
            //todo intent
            val task: Task = intent.getParcelableArrayListExtra<Task>(TASK_KEY)?.first() ?:
                throw IllegalStateException("task passed from alarm is null")


            createNotificationChannel(context)
            showNotification(context, task)
        }
    }

    private fun showNotification(context: Context, task: Task) {

        val intent =
            Intent(context, MainActivity::class.java).putParcelableArrayListExtra(
                TASK_KEY, arrayListOf(task)
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
//         todo addAction(confirm)
//         todo addAction(delay)
        Log.d(MainActivity.TAG,"showing notification")

        NotificationManagerCompat.from(context).notify(0, notification)

    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(MainActivity.TAG,"creating channel")

            val channel =
                NotificationChannel(CHANNEL_ID, "task channel", NotificationManager.IMPORTANCE_HIGH)
                    .apply { description = "todo string.xml powiadomienia nadchodzacych zadan" }
            NotificationManagerCompat.from(context).createNotificationChannel(channel)
        }
    }

    companion object {
        const val TASK_KEY = "task"
        const val CHANNEL_ID = "pending task Notifications channel"
        const val TASK_NOTIFICATION_ID = 0
        const val CREATE_NOTIFICATION_ACTION = "create notification action"

    }
}