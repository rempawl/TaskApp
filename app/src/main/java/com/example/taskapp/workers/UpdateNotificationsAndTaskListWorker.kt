package com.example.taskapp.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.taskapp.MyApp
import com.example.taskapp.database.entities.Task
import com.example.taskapp.repos.task.TaskRepository
import com.example.taskapp.repos.task.TaskRepositoryInterface
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import javax.inject.Inject

typealias DatePredicate = (LocalDate, Task) -> (Boolean)

class UpdateNotificationsAndTaskListWorker constructor(
    appContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters) {


    companion object {
        private const val TASK_KEY = "task"
        const val WORK_NAME = "UpdateNotificationDatesWorker"
        private val TODAY: LocalDate = LocalDate.now()
        val TOMORROW: LocalDate = LocalDate.ofEpochDay(TODAY.toEpochDay() + 1)
        const val CURRENT_DATE_KEY = "currentDate"
    }

    //todo constructor inject WorkerFactory
    @Inject
    lateinit var taskRepo: TaskRepositoryInterface

    @Inject
    lateinit var alarmCreator: AlarmCreator

    override suspend fun doWork(): Result {
        (applicationContext as MyApp).appComponent.inject(this)

        val allTasks = taskRepo.getTasks()
        if (allTasks.isEmpty()) return Result.success()

        if (allTasks.first() == TaskRepository.ERROR_TASK) {
            return Result.retry()
        }
        val tasks = allTasks.filter { task -> task.reminder != null }

        val preferences = applicationContext
            .getSharedPreferences(MyApp.PREFERENCES_NAME, Context.MODE_PRIVATE)
        val currentDate = preferences.getLong(CURRENT_DATE_KEY, -1)
        val predicate: DatePredicate = { date, task ->
            task.reminder!!.realizationDate == date
                    && task.reminder.notificationTime.isSet
        }

        if (currentDate == -1L || LocalDate.ofEpochDay(currentDate).isBefore(TODAY)) {
            val todayTasks = updateTaskList(tasks)
            setTodayNotifications(todayTasks, predicate)
        }
        if (LocalDate.ofEpochDay(currentDate).isEqual(TODAY)) {
            //todo setting tomorrow notifs after updating tasks
            val tomorrowTasksIds = setTomorrowNotifications(tasks, predicate)
            alarmCreator.setUpdateTaskListAlarm(tomorrowTasksIds)

            preferences.edit().putLong(CURRENT_DATE_KEY, TOMORROW.toEpochDay()).apply()
        }


        return Result.success()
    }


    private suspend fun updateTaskList(tasks: List<Task>): List<Task> {
        val partitionedTasks = tasks
            .partition { task -> task.updateRealizationDate() != null }

        taskRepo.updateTasks(partitionedTasks.first)

        return partitionedTasks.toList()[0]
            .filter { task ->
            task.reminder!!.realizationDate.isEqual(TODAY)
        }
    }

    private fun setTodayNotifications(tasks: List<Task>, predicate: DatePredicate) {
        tasks
            .filter { task ->
                predicate(TODAY, task)
                        && task.reminder!!.notificationTime.convertToLocalTime()
                    .isAfter(LocalTime.now())
            }.forEach { task -> alarmCreator.setTaskNotificationAlarm(task) }

    }

    private fun setTomorrowNotifications(tasks: List<Task>, predicate: DatePredicate): List<Long> {
        val tomorrowTasks = tasks
            .filter { task -> predicate(TOMORROW, task) }
        tomorrowTasks
            .forEach { task -> alarmCreator.setTaskNotificationAlarm(task) }
        return tomorrowTasks.map { task -> task.taskID }
    }

}


data class TomorrowTasks(val taskId: Long)