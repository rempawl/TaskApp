package com.example.taskapp.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.taskapp.MyApp.Companion.TODAY
import com.example.taskapp.database.AppDataBase
import com.example.taskapp.database.entities.task.DefaultTask
import com.example.taskapp.repos.task.TaskLocalDataSource
import com.example.taskapp.repos.task.TaskRepository
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.alarmCreator.AlarmCreator
import com.example.taskapp.utils.alarmCreator.AlarmCreatorImpl
import com.example.taskapp.utils.notification.NotificationIntentFactoryImpl
import com.example.taskapp.utils.sharedPreferences.SharedPreferencesHelper
import com.example.taskapp.utils.sharedPreferences.SharedPreferencesHelperImpl
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

typealias DatePredicate = (LocalDate, DefaultTask) -> (Boolean)

/**
 * class that checks if today tasks are up to date
 * sets reminder update alarm
 */
class UpdateRemindersWorker constructor(
    appContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters) {


    private val datePredicate: DatePredicate = { date, task ->
        task.reminder!!.realizationDate == date
                && task.reminder.notificationTime.isSet
    }


    //todo constructor inject
    private val sharedPreferencesHelper: SharedPreferencesHelper =
        SharedPreferencesHelperImpl(applicationContext)

    private val alarmCreator: AlarmCreator = AlarmCreatorImpl(
        applicationContext,
        NotificationIntentFactoryImpl(context = appContext)
    )

    //    @Inject
    private val taskRepo: TaskRepositoryInterface = TaskRepository(
        TaskLocalDataSource(
            AppDataBase.getInstance(applicationContext).taskDao()
        )
    )


    override suspend fun doWork(): Result {

        val result = taskRepo.getTasks()

        @Suppress("UNCHECKED_CAST")
        return when (result) {
            is com.example.taskapp.database.Result.Error -> Result.retry()
            is com.example.taskapp.database.Result.Success<*> -> updateTasks(result.items as List<DefaultTask>)
        }

    }

    private suspend fun updateTasks(allTasks: List<DefaultTask>): Result {
        if (allTasks.isEmpty()) return Result.success()

        val tasks = allTasks.filter { task -> task.reminder != null }

        val currentDate = sharedPreferencesHelper.getCurrentDate()

        if (currentDate == -1L || LocalDate.ofEpochDay(currentDate).isBefore(TODAY)) {
            val updatedTasks = updateTaskList(tasks)
            setTodayNotifications(updatedTasks)
        }
        if (LocalDate.ofEpochDay(currentDate).isEqual(TODAY)) {
            alarmCreator.setUpdateTaskListAlarm()
        }

        return Result.success()

    }

    private suspend fun updateTaskList(tasks: List<DefaultTask>): List<DefaultTask> {
        val partitionedTasks = tasks
            .partition { task -> task.updateRealizationDate() != null }

        taskRepo.updateTasks(partitionedTasks.first)

        return partitionedTasks.toList()[0]
    }

    private fun setTodayNotifications(tasks: List<DefaultTask>) {
        tasks
            .filter { task ->
                datePredicate(TODAY, task)
                        && task.reminder!!.notificationTime.convertToLocalTime()
                    .isAfter(LocalTime.now())
            }.forEach { task ->
                alarmCreator.setTaskNotificationAlarm(task)
            }
    }


    companion object {
        private const val TASK_KEY = "task"
        const val WORK_NAME = "UpdateNotificationDatesWorker"
    }

}

