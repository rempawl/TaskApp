package com.example.taskapp.broadcastReceivers.notification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.taskapp.MyApp.Companion.CREATE_NOTIFICATION_ACTION
import com.example.taskapp.MyApp.Companion.TASK_CHANNEL_ID
import com.example.taskapp.MyApp.Companion.TASK_KEY
import com.example.taskapp.MyApp.Companion.TASK_NOTIFICATION_ID
import com.example.taskapp.R
import com.example.taskapp.data.task.TaskMinimal
import com.example.taskapp.data.task.TaskMinimal.Companion.TASK_ID_KEY
import com.example.taskapp.utils.notification.NotificationIntentFactory
import com.example.taskapp.utils.notification.NotificationIntentFactoryImpl
import com.example.taskapp.utils.notification.NotificationManagerHelper
import com.example.taskapp.utils.notification.NotificationManagerHelperImpl

class CreateTaskNotificationBroadcastReceiver : BroadcastReceiver() {


    //todo inject
    private lateinit var notificationIntentFactory: NotificationIntentFactory
    lateinit var notificationManagerHelper: NotificationManagerHelper

    override fun onReceive(context: Context, intent: Intent) {
        notificationIntentFactory = NotificationIntentFactoryImpl(context)
        notificationManagerHelper = NotificationManagerHelperImpl(context)

        val action = intent.action
        if (action == CREATE_NOTIFICATION_ACTION) {
            notificationManagerHelper.createNotificationChannel()
            showNotification(context, TaskMinimal.from(intent))
        }
    }


    private fun createNotification(
        context: Context,
        task: TaskMinimal
    ): NotificationCompat.Builder {
        val confirmAction = createConfirmAction(context, task)
        val delay30MinAction = createDelay30MinAction(context, task)
        val delayCustomTimeAction = createDelayCustomTimeAction(context, task)

        val bigTextStyle = NotificationCompat
            .BigTextStyle()
            .bigText("${task.description} ")

        return NotificationCompat.Builder(context, TASK_CHANNEL_ID)
            .setAutoCancel(true)
            .setContentTitle(context.getString(R.string.task_notification_title))
            .setContentText(" ${task.name} ")
            .setSmallIcon(R.drawable.ic_alarm_on_black_24dp)
            .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
            .addAction(confirmAction.build())
            .addAction(delay30MinAction.build())
            .addAction(delayCustomTimeAction.build())
            .setAutoCancel(true)
            .setStyle(bigTextStyle)
    }

    private fun showNotification(context: Context, task: TaskMinimal) {
        val notification = createNotification(context, task)
            .build()

        NotificationManagerCompat.from(context).notify(TASK_NOTIFICATION_ID, notification)

    }

    private fun createDelayCustomTimeAction(
        context: Context,
        task: TaskMinimal
    ): NotificationCompat.Action.Builder {
        val args = Bundle().apply { putParcelable(TASK_KEY, task) }
        val deepLink = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.main_navigation)
            .setDestination(R.id.navigation_pick_custom_delay)
            .setArguments(args)
            .createPendingIntent()


        val title = context.getString(R.string.delay_later)
        return NotificationCompat.Action
            .Builder(R.drawable.ic_later_black_24dp, title, deepLink)
    }


    private fun createConfirmAction(
        context: Context,
        task: TaskMinimal
    ): NotificationCompat.Action.Builder {
        val confirmIntent = Intent(context, ConfirmTaskCompletedReceiver::class.java)
            .putExtra(TASK_ID_KEY, task.taskID)

        val confirmPendingIntent =
            PendingIntent.getBroadcast(context, 0, confirmIntent, PendingIntent.FLAG_UPDATE_CURRENT)


        return NotificationCompat.Action.Builder(
            R.drawable.ic_task_completed_24dp,
            context.getString(R.string.confirm),
            confirmPendingIntent
        )

    }


    private fun createDelay30MinAction(
        context: Context,
        task: TaskMinimal
    ): NotificationCompat.Action.Builder {
        val intent = notificationIntentFactory.createDelayNotificationIntent(
            30, task
        )

        val delay30Pending =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val title = context.getString(R.string.delay_30_min)

        return NotificationCompat.Action
            .Builder(R.drawable.ic_alarm_on_black_24dp, title, delay30Pending)


    }


    companion object

}