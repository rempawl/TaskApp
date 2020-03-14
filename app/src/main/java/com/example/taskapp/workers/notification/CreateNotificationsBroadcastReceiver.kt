package com.example.taskapp.workers.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.taskapp.MainActivity
import com.example.taskapp.R
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.utils.NotificationIntentFactory

/**
 * class responsible for creating and showing task notifications
 */
class CreateNotificationBroadcastReceiver :
    BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && context != null) {
            val action = intent.action
            if (action == CREATE_NOTIFICATION_ACTION) {
                createNotificationChannel(context)
                showNotification(context, createTaskMinimal(intent))
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
        Log.d(MainActivity.TAG,"building actions")

        val confirmAction = createConfirmAction(context, task)
        val delay30MinAction = createDelay30MinAction(context, task)
        val delayCustomTimeAction = createDelayCustomTimeAction(context, task)

        Log.d(MainActivity.TAG,"building notif")

        val notification = NotificationCompat.Builder(
                context,
                CHANNEL_ID
            )
            .setAutoCancel(true)
            .setContentTitle("todo strings.xml Czas wykonac zadanie")
            .setContentText(" ${task.name}  ${task.description} ")
            .setSmallIcon(R.drawable.ic_alarm_on_black_24dp)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .addAction(confirmAction)
            .addAction(delay30MinAction)
            .addAction(delayCustomTimeAction)
            .build()
        Log.d(MainActivity.TAG,"Showing notifi")

        NotificationManagerCompat.from(context).notify(0, notification)

    }

    private fun createDelayCustomTimeAction(
        context: Context,
        task: TaskMinimal
    ): NotificationCompat.Action {
        val args = Bundle().apply { putParcelable(TASK_KEY, task) }
        val deepLink = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.main_navigation)
            .setDestination(R.id.navigation_pick_custom_delay)
            .setArguments(args)
            .createPendingIntent()

        val title = context.getString(R.string.delay_later)
        return NotificationCompat.Action
            .Builder(R.drawable.ic_later_black_24dp, title, deepLink)
            .build()
    }


    private fun createConfirmAction(
        context: Context,
        task: TaskMinimal
    ): NotificationCompat.Action {
        val confirmIntent = Intent(context, ConfirmTaskCompletedReceiver::class.java)
            .putExtra(TASK_ID_KEY, task.taskID)

        val confirmPendingIntent =
            PendingIntent.getBroadcast(context, 0, confirmIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Action.Builder(
            R.drawable.ic_task_completed_24dp,
            context.getString(R.string.confirm),
            confirmPendingIntent
        ).build()

    }


    private fun createDelay30MinAction(
        context: Context,
        task: TaskMinimal
    ): NotificationCompat.Action {
        val intent = NotificationIntentFactory.createDelayNotificationIntent(
            context, 30, task
        )

        val delay30Pending =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val title = context.getString(R.string.delay_30_min)

        return NotificationCompat.Action
            .Builder(R.drawable.ic_alarm_on_black_24dp, title, delay30Pending)
            .build()

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
        const val TASK_KEY = "task"
        const val CHANNEL_ID = "pending task Notifications channel"
        const val TASK_NOTIFICATION_ID = 0x1
        const val CREATE_NOTIFICATION_ACTION = "create notification action"

    }
}