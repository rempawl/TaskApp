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
import com.example.taskapp.database.entities.TaskMinimal

class CreateNotificationBroadcastReceiver :
    BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && context != null) {
            val action = intent.action
            if (action == CREATE_NOTIFICATION_ACTION) {
                Log.d(MainActivity.TAG, action)
                createNotificationChannel(context)
                showNotification(context, createTaskMinimal(intent))
            } else {
                Log.d(MainActivity.TAG, action ?: "null")
            }


        }
    }

    private fun createTaskMinimal(intent: Intent): TaskMinimal {
        val name = intent.getStringExtra(TASK_NAME_KEY) ?: "error"
        val desc = intent.getStringExtra(TASK_DESC_KEY) ?: "error"
        val id = intent.getLongExtra(TASK_ID_KEY, -1)

        return TaskMinimal(id, name, desc)
    }

    private fun showNotification(context: Context, task: TaskMinimal) {

        val intent =
            Intent(context, MainActivity::class.java).putExtra(TASK_ID_KEY,task.taskID)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

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
        Log.d(MainActivity.TAG, "showing notification")

        NotificationManagerCompat.from(context).notify(0, notification)

    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(MainActivity.TAG, "creating channel")

            val channel =
                NotificationChannel(CHANNEL_ID, "task channel", NotificationManager.IMPORTANCE_HIGH)
                    .apply { description = "todo string.xml powiadomienia nadchodzacych zadan" }
            NotificationManagerCompat.from(context).createNotificationChannel(channel)
        }
    }

    companion object {
        const val TASK_NAME_KEY = "task name"
        const val TASK_DESC_KEY = "task desc"
        const val TASK_ID_KEY = "task id"
        const val CHANNEL_ID = "pending task Notifications channel"
        const val TASK_NOTIFICATION_ID = 0x1
        const val CREATE_NOTIFICATION_ACTION = "create notification action"

    }
}