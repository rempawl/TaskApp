package com.example.taskapp.workers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
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

        val elapsed = SystemClock.elapsedRealtime()
        val notifyTime = LocalDateTime.of(date, time).toInstant(ZONE_OFFSET).toEpochMilli()
//test        manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, elapsed, 30000, pending)

        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, notifyTime, pending)
    }

    fun setUpdateTaskListAlarm(tomorrowTasksIds: List<Long>) {
        val updateTime = LocalDateTime.of(TODAY, LocalTime.of(23, 59))
            .toInstant(ZONE_OFFSET).toEpochMilli()

        val intent = Intent(context,UpdateTaskListReceiver::class.java)
//            .putStringArrayListExtra(TASK_ID_LIST_KEY,)
        val pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        val interval = TimeUnit.DAYS.toMillis(1)
        manager.setInexactRepeating(AlarmManager.RTC,updateTime,interval,pendingIntent)

    }


    companion object {
        val ZONE_OFFSET: ZoneOffset = OffsetDateTime.now().offset
        const val TASK_NAME_KEY = "task name"
        const val TASK_DESC_KEY = "task desc"
        const val TASK_ID_KEY = "task id"
        const val TASK_ID_LIST_KEY = "list of task ids"
    }

}