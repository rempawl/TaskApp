package com.example.taskapp.utils.alarmCreator

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.SystemClock
import com.example.taskapp.data.task.Task
import com.example.taskapp.data.task.TaskMinimal
import com.example.taskapp.utils.DateUtils.TODAY
import com.example.taskapp.utils.DateUtils.TOMORROW
import com.example.taskapp.utils.DateUtils.ZONE_OFFSET
import com.example.taskapp.utils.notification.NotificationIntentFactory
import org.threeten.bp.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AlarmCreatorImpl @Inject constructor(
    private val context: Context,
    private val notificationIntentFactory: NotificationIntentFactory
) : AlarmCreator {

    private fun createManager(context: Context) =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    //todo inject
//    private val notificationIntentFactory : NotificationIntentFactory =
//        NotificationIntentFactoryImpl(context)


    override fun setTaskNotificationAlarm(task: Task, isToday: Boolean) {
        val manager = createManager(context)

        val intent =
            notificationIntentFactory.createNotificationReceiverIntent(task.toTaskMinimal())
        val date = if (isToday) TODAY else TOMORROW

        val pending = PendingIntent
            .getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val time = task.reminder!!.notificationTime.convertToLocalTime()

        val notifyTime = LocalDateTime.of(date, time).toInstant(ZONE_OFFSET).toEpochMilli()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, notifyTime, pending)
        }
    }


    override fun setDelayAlarm(task: TaskMinimal, interval: Long) {
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = notificationIntentFactory.createNotificationReceiverIntent(task)
        val pending =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val triggerTime = SystemClock.elapsedRealtime() + TimeUnit.MINUTES.toMillis(interval)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pending)
        } else {
            TODO()
        }
    }

    companion object {
        const val DATE_KEY = "date"

    }

}

