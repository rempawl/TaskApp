package com.example.taskapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.taskapp.MyApp.Companion.TOMORROW
import com.example.taskapp.database.AppDataBase
import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.repos.task.TaskLocalDataSource
import com.example.taskapp.repos.task.TaskRepository
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.alarmCreator.AlarmCreator
import com.example.taskapp.utils.alarmCreator.AlarmCreatorImpl
import com.example.taskapp.utils.notification.NotificationIntentFactoryImpl
import com.example.taskapp.utils.sharedPreferences.SharedPreferencesHelper
import com.example.taskapp.utils.sharedPreferences.SharedPreferencesHelperImpl
import com.example.taskapp.workers.DatePredicate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * class responsible for updating realization dates of tasks and setting alarms for tomorrow tasks
 */
class UpdateTomorrowRemindersReceiver :
    BroadcastReceiver() {

    //todo constructor inject
//    @Inject
    private lateinit var taskRepository: TaskRepositoryInterface

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var alarmCreator: AlarmCreator

    override fun onReceive(context: Context, intent: Intent) {
        //todo inject
        sharedPreferencesHelper = SharedPreferencesHelperImpl(context)
        alarmCreator = AlarmCreatorImpl(context, NotificationIntentFactoryImpl(context))


        CoroutineScope(Dispatchers.Default).launch {
            taskRepository == TaskRepository(
                TaskLocalDataSource(
                    AppDataBase
                        .getInstance(context).taskDao()
                )
            )
            val tasks: List<DefaultTask> = taskRepository.getTasks()

            if (tasks.first() == TaskRepository.ERROR_TASK) {
                //if repo returns error UpdateNotificationsWorker will have to update the reminders
                sharedPreferencesHelper.setErrorCurrentDate()
                return@launch
            }

            val tasksToUpdate = tasks.filter { task -> task.reminder != null }
            if (tasksToUpdate.isEmpty()) return@launch

            val updatedTasks = updateTaskList(tasksToUpdate)
            setTomorrowNotifications(updatedTasks, context)

            sharedPreferencesHelper.updateCurrentDate(TOMORROW)
        }
    }

    private suspend fun updateTaskList(tasks: List<DefaultTask>): List<DefaultTask> {
        val partitionedTasks = tasks
            .partition { task -> task.updateRealizationDate() != null }

        taskRepository.updateTasks(partitionedTasks.first)
        return partitionedTasks.toList()[0]
    }


    private fun setTomorrowNotifications(tasks: List<DefaultTask>, context: Context) {
        val tomorrowTasks = tasks
            .filter { task ->
                DATE_PREDICATE(
                    TOMORROW,
                    task
                )
            }
        tomorrowTasks
            .forEach { task -> alarmCreator.setTaskNotificationAlarm(task ) }
    }

    companion object {
        private val DATE_PREDICATE: DatePredicate = { date, task ->
            task.reminder!!.realizationDate == date
                    && task.reminder.notificationTime.isSet
        }

    }

}

