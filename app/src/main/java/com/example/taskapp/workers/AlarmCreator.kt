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
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

class AlarmCreator @Inject constructor(private val context: Context) {

    fun setTaskNotificationAlarm(task: Task , isToday: Boolean= false){
        val manager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context,CreateNotificationBroadcastReceiver::class.java).apply{
            putParcelableArrayListExtra(TASK_KEY,arrayListOf(task))
            action = CreateNotificationBroadcastReceiver.CREATE_NOTIFICATION_ACTION
        }

        val date = if(isToday) TODAY else TOMORROW
        val pending = PendingIntent.getBroadcast(context, 0, intent, 0)
        val time = task.reminder!!.notificationTime.convertToLocalTime()

        val elapsed = SystemClock.elapsedRealtime()
        val notifyTime = LocalDateTime.of(date, time).toInstant(OffsetDateTime.now().offset)
//                manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,elapsed,30000,pending)
//        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, notifyTime.toEpochMilli(), pending)
    }
    companion object{
        const val TASK_KEY = "task"
    }

}