package com.example.taskapp.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.taskapp.MyApp.Companion.TODAY
import com.example.taskapp.data.task.Task
import com.example.taskapp.dataSources.task.TaskRepository
import com.example.taskapp.dataSources.task.TaskRepositoryImpl
import com.example.taskapp.database.AppDataBase
import com.example.taskapp.utils.alarmCreator.AlarmCreator
import com.example.taskapp.utils.alarmCreator.AlarmCreatorImpl
import com.example.taskapp.utils.notification.NotificationIntentFactoryImpl
import com.example.taskapp.utils.sharedPreferences.SharedPreferencesHelper
import com.example.taskapp.utils.sharedPreferences.SharedPreferencesHelperImpl
import kotlinx.coroutines.flow.first
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

typealias DatePredicate = (LocalDate, Task) -> (Boolean)

/**
 * class that checks if today tasks are up to date  updates reminders and sets alarms
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
    private val taskRepo: TaskRepository = TaskRepositoryImpl(
        AppDataBase.getInstance(applicationContext).taskDao()
    )


    @Suppress("UNCHECKED_CAST")
    override suspend fun doWork(): Result {
        return try {
            val result =
                taskRepo.getTasks().first { result -> result.checkIfIsSuccessAndListOf<Task>() }
            result as com.example.taskapp.data.Result.Success
            filterAndUpdateTasks(result.data as List<Task>)
        } catch (e: Exception) {
            Result.retry()
        }


    }

    private suspend fun filterAndUpdateTasks(allTasks: List<Task>): Result {
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

    private suspend fun updateTaskList(tasks: List<Task>): List<Task> {
        val partitionedTasks = tasks
            .partition { task -> task.updateRealizationDate() != null }

        taskRepo.updateTasks(partitionedTasks.first)

        return partitionedTasks.toList()[0]
    }

    private fun setTodayNotifications(tasks: List<Task>) {
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

