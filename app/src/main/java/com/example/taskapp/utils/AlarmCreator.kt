package com.example.taskapp.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import com.example.taskapp.MyApp.Companion.TODAY
import com.example.taskapp.MyApp.Companion.TOMORROW
import com.example.taskapp.MyApp.Companion.ZONE_OFFSET
import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.database.entities.toTaskMinimal
import com.example.taskapp.utils.notification.NotificationIntentFactoryImpl.createNotificationReceiverIntent
import com.example.taskapp.workers.UpdateTomorrowRemindersReceiver
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import java.util.concurrent.TimeUnit


object AlarmCreator {

    private fun createManager(context: Context) =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    fun setTaskNotificationAlarm(task: DefaultTask, isToday: Boolean = false, context: Context) {
        val manager =
            createManager(context)

        val intent = createNotificationReceiverIntent(task.toTaskMinimal(), context)
        val date = if (isToday) TODAY else TOMORROW

        val pending = PendingIntent
            .getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val time = task.reminder!!.notificationTime.convertToLocalTime()

        val notifyTime = LocalDateTime.of(date, time).toInstant(ZONE_OFFSET).toEpochMilli()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, notifyTime, pending)
        }
    }

    fun setUpdateTaskListAlarm(context: Context) {
        val manager =
            createManager(context)

        val updateTime = LocalDateTime.of(TODAY, LocalTime.of(23, 55))
            .toInstant(ZONE_OFFSET).toEpochMilli()

        val intent = Intent(context, UpdateTomorrowRemindersReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val interval = TimeUnit.DAYS.toMillis(1)
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, updateTime, interval, pendingIntent)
    }


    fun setDelayAlarm(task: TaskMinimal, interval: Long = 30, context: Context) {
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = createNotificationReceiverIntent(task, context)
        val pending =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val triggerTime = SystemClock.elapsedRealtime() + TimeUnit.MINUTES.toMillis(interval)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pending)
        }else{
            TODO()
        }
    }

    const val DATE_KEY = "date"


}

