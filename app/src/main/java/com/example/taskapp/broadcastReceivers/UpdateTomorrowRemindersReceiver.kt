package com.example.taskapp.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.taskapp.MyApp.Companion.TOMORROW
import com.example.taskapp.data.Result
import com.example.taskapp.data.task.Task
import com.example.taskapp.dataSources.task.TaskRepository
import com.example.taskapp.dataSources.task.TaskRepositoryImpl
import com.example.taskapp.database.AppDataBase
import com.example.taskapp.utils.alarmCreator.AlarmCreator
import com.example.taskapp.utils.alarmCreator.AlarmCreatorImpl
import com.example.taskapp.utils.notification.NotificationIntentFactoryImpl
import com.example.taskapp.utils.providers.DefaultDispatcherProvider
import com.example.taskapp.utils.providers.DispatcherProvider
import com.example.taskapp.utils.sharedPreferences.SharedPreferencesHelper
import com.example.taskapp.utils.sharedPreferences.SharedPreferencesHelperImpl
import com.example.taskapp.workers.DatePredicate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * class responsible for updating realization dates of tasks and setting alarms for tomorrow tasks
 */
class UpdateTomorrowRemindersReceiver :
    BroadcastReceiver() {

    //todo constructor inject
//    @Inject
    private lateinit var taskRepository: TaskRepository

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var alarmCreator: AlarmCreator

    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
    private val coroutineScope = CoroutineScope(dispatcherProvider.defaultDispatcher + Job())


    override fun onReceive(context: Context, intent: Intent) {
        //todo inject
        sharedPreferencesHelper = SharedPreferencesHelperImpl(context)
        alarmCreator = AlarmCreatorImpl(context, NotificationIntentFactoryImpl(context))
        taskRepository = TaskRepositoryImpl(AppDataBase.getInstance(context).taskDao())

        @Suppress("UNCHECKED_CAST")
        coroutineScope.launch {
            taskRepository.getTasks().onEach { result ->
                when (result) {
                    is Result.Error -> setError()
                    is Result.Success<*> -> updateTasks(result.data as List<Task>)
                }
            }
        }
    }

    private fun setError() {
        //if repo returns error UpdateNotificationsWorker will have to update the reminders
        sharedPreferencesHelper.setErrorCurrentDate()
    }

    private suspend fun updateTasks(tasks: List<Task>) {
        val tasksToUpdate = tasks.filter { task -> task.reminder != null }
        if (tasksToUpdate.isEmpty()) return
        val updatedTasks = updateTaskList(tasksToUpdate)
        setTomorrowNotifications(updatedTasks)

        sharedPreferencesHelper.updateCurrentDate(TOMORROW)
    }

    private suspend fun updateTaskList(tasks: List<Task>): List<Task> {
        val partitionedTasks = tasks
            .partition { task -> task.updateRealizationDate() != null }

        taskRepository.updateTasks(partitionedTasks.first)
        return partitionedTasks.toList()[0]
    }


    private fun setTomorrowNotifications(tasks: List<Task>) {
        tasks.asSequence()
            .filter { task -> DATE_PREDICATE(TOMORROW, task) }
            .forEach { task -> alarmCreator.setTaskNotificationAlarm(task) }
    }

    companion object {
        private val DATE_PREDICATE: DatePredicate = { date, task ->
            task.reminder!!.realizationDate == date
                    && task.reminder.notificationTime.isSet
        }

    }

}

