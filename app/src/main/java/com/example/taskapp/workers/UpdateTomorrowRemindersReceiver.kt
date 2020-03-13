package com.example.taskapp.workers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.taskapp.MyApp
import com.example.taskapp.MyApp.Companion.TOMORROW
import com.example.taskapp.database.entities.Task
import com.example.taskapp.repos.task.TaskRepository
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.SharedPreferencesHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateTomorrowRemindersReceiver @Inject constructor(
    private val taskRepository: TaskRepositoryInterface,
    private val alarmCreator: AlarmCreator,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        CoroutineScope(Dispatchers.Default).launch {
            val tasks: List<Task> = taskRepository.getTasks()

            if (tasks.first() == TaskRepository.ERROR_TASK) {
                //if repo returns error UpdateNotificationsWorker will have to update the reminders
                sharedPreferencesHelper.setErrorCurrentDate()
                return@launch
            }

            val tasksToUpdate = tasks.filter { task -> task.reminder != null }
            if (tasksToUpdate.isEmpty()) return@launch

            val updatedTasks = updateTaskList(tasksToUpdate)
            setTomorrowNotifications(updatedTasks)

            sharedPreferencesHelper.updateCurrentDate(TOMORROW)
        }
    }

    private suspend fun updateTaskList(tasks: List<Task>): List<Task> {
        val partitionedTasks = tasks
            .partition { task -> task.updateRealizationDate() != null }

        taskRepository.updateTasks(partitionedTasks.first)
        return partitionedTasks.toList()[0]
    }


    private fun setTomorrowNotifications(tasks: List<Task>) {
        val tomorrowTasks = tasks
            .filter { task -> DATE_PREDICATE(MyApp.TOMORROW, task) }
        tomorrowTasks
            .forEach { task -> alarmCreator.setTaskNotificationAlarm(task) }
    }

    companion object {
        private val DATE_PREDICATE: DatePredicate = { date, task ->
            task.reminder!!.realizationDate == date
                    && task.reminder.notificationTime.isSet
        }

    }

}

