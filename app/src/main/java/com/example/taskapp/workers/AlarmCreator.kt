package com.example.taskapp.workers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.taskapp.MyApp.Companion.TODAY
import com.example.taskapp.MyApp.Companion.TOMORROW
import com.example.taskapp.database.entities.Task
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

        val intent = Intent(context, CreateNotificationBroadcastReceiver::class.java).apply {
            putExtra(TASK_NAME_KEY, task.name)
            putExtra(TASK_DESC_KEY, task.description)
            putExtra(TASK_ID_KEY, task.taskID)
            action = CreateNotificationBroadcastReceiver.CREATE_NOTIFICATION_ACTION
        }

        val date = if (isToday) TODAY else TOMORROW
        val pending =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val time = task.reminder!!.notificationTime.convertToLocalTime()

        val notifyTime = LocalDateTime.of(date, time).toInstant(ZONE_OFFSET).toEpochMilli()

        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, notifyTime, pending)
    }

    fun setUpdateTaskListAlarm() {
        val updateTime = LocalDateTime.of(TODAY, LocalTime.of(23, 50))
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
        const val TASK_NAME_KEY = "task name"
        const val TASK_DESC_KEY = "task desc"
        const val TASK_ID_KEY = "task id"
        const val TASK_ID_LIST_KEY = "list of task ids"
    }

}