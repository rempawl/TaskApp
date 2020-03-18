package com.example.taskapp.workers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import com.example.taskapp.MyApp.Companion.TODAY
import com.example.taskapp.MyApp.Companion.TOMORROW
import com.example.taskapp.database.entities.Task
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.utils.notification.NotificationIntentFactory.Companion.createNotificationReceiverIntent
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class AlarmCreator @Inject constructor(private val context: Context) {

    private val manager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    fun setTaskNotificationAlarm(task: Task, isToday: Boolean = false) {
        val intent = createNotificationReceiverIntent(task.toTaskMinimal(), context)
        val date = if (isToday) TODAY else TOMORROW
        val pending =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val time = task.reminder!!.notificationTime.convertToLocalTime()

        val notifyTime = LocalDateTime.of(date, time).toInstant(ZONE_OFFSET).toEpochMilli()

        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, notifyTime, pending)
    }
    fun setUpdateTaskListAlarm() {
        val updateTime = LocalDateTime.of(TODAY, LocalTime.of(23, 55))
            .toInstant(ZONE_OFFSET).toEpochMilli()

        val intent = Intent(context, UpdateTomorrowRemindersReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val interval = TimeUnit.DAYS.toMillis(1)
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, updateTime, interval, pendingIntent)
    }



//    fun setRetryAlarm( date : LocalDate){
//        val interval = TimeUnit.MINUTES.toMillis(10)
//        val updateTime =  SystemClock.elapsedRealtime() + interval
//
//        val intent = Intent(context,UpdateTomorrowRemindersReceiver::class.java)
//            .putExtra(DATE_KEY,date.toEpochDay())
//        val pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
//
//        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,updateTime,pendingIntent)
//
//
//    }


    companion object {
        const val DATE_KEY = "date"
        val ZONE_OFFSET: ZoneOffset = OffsetDateTime.now().offset

        fun setDelayAlarm(task: TaskMinimal, interval: Long = 30, context: Context) {
            val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = createNotificationReceiverIntent(task, context)
            val pending =
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val triggerTime = SystemClock.elapsedRealtime() + TimeUnit.MINUTES.toMillis(interval)

            manager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pending)
        }

    }

}

fun Task.toTaskMinimal(): TaskMinimal {
    return TaskMinimal(
        taskID = this.taskID,
        name = this.name,
        description = this.description
    )
}
