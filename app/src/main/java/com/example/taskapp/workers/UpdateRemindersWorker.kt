package com.example.taskapp.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.taskapp.MyApp
import com.example.taskapp.MyApp.Companion.TODAY
import com.example.taskapp.database.entities.Task
import com.example.taskapp.repos.task.TaskRepository
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.SharedPreferencesHelper
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import javax.inject.Inject

typealias DatePredicate = (LocalDate, Task) -> (Boolean)

/**
 * class that checks if today tasks are up to date
 * *sets reminder update alarm
 */
class UpdateRemindersWorker constructor(
    appContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters) {



    //todo constructor inject WorkerFactory
    @Inject
    lateinit var taskRepo: TaskRepositoryInterface
    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper


    override suspend fun doWork(): Result {
        (applicationContext as MyApp).appComponent.inject(this)

        val allTasks = taskRepo.getTasks()
        if (allTasks.isEmpty()) return Result.success()

        if (allTasks.first() == TaskRepository.ERROR_TASK) {
            return Result.retry()
        }
        val tasks = allTasks.filter { task -> task.reminder != null }

        val currentDate = sharedPreferencesHelper.getCurrentDate()

        if (currentDate == -1L || LocalDate.ofEpochDay(currentDate).isBefore(TODAY)) {
            val updatedTasks = updateTaskList(tasks)
            setTodayNotifications(updatedTasks)
        }
        if (LocalDate.ofEpochDay(currentDate).isEqual(TODAY)) {
            AlarmCreator.setUpdateTaskListAlarm(applicationContext)
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
                DATE_PREDICATE(TODAY, task)
                        && task.reminder!!.notificationTime.convertToLocalTime()
                    .isAfter(LocalTime.now())
            }.forEach { task -> AlarmCreator.setTaskNotificationAlarm(task,context = applicationContext) }
    }

    companion object {
    private val DATE_PREDICATE: DatePredicate = { date, task ->
            task.reminder!!.realizationDate == date
                    && task.reminder.notificationTime.isSet
        }

        private const val TASK_KEY = "task"
        const val WORK_NAME = "UpdateNotificationDatesWorker"
    }

}

