package com.example.taskapp.work

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.taskapp.database.AppDataBase
import com.example.taskapp.database.entities.Task
import com.example.taskapp.repos.task.TaskLocalDataSource
import com.example.taskapp.repos.task.TaskRepository
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime

class UpdateNotificationsWorker(appContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(appContext, workerParameters) {


    companion object {
        const val WORK_NAME = "UpdateNotificationDatesWorker"
        val TOMORROW: LocalDate = LocalDate.ofEpochDay(LocalDate.now().toEpochDay() + 1)
    }


    override suspend fun doWork(): Result {
        val taskDataSource =
            TaskLocalDataSource(AppDataBase.getInstance(applicationContext).taskDao())
        val taskRepo = TaskRepository(taskDataSource)
        val tasks = taskRepo.getTasks()
        if (tasks.first() == TaskRepository.ERROR_TASK) {
            return Result.retry()
        } else {
            tasks
                .filter { task -> task.reminder != null && task.reminder.notificationDate == TOMORROW }
                .forEach { task -> setAlarm(task) }
        }
        return Result.success()
    }

    private fun setAlarm(task: Task) {
        val manager =
            applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(
            applicationContext,
            NotificationBroadcastReceiver::class.java
        ).putParcelableArrayListExtra(
            "task",
            arrayListOf(task)
        )

        val pending = PendingIntent.getBroadcast(applicationContext, 0, intent, 0)
        val time = task.reminder!!.notificationTime.convertToLocalTime()
        val notifyTime = LocalDateTime.of(TOMORROW, time).toEpochSecond(OffsetDateTime.now().offset)
        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, notifyTime, pending)
    }
}