package com.example.taskapp.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.taskapp.dataSources.task.TaskRepository
import com.example.taskapp.dataSources.task.TaskRepositoryImpl
import com.example.taskapp.database.AppDataBase
import com.example.taskapp.utils.alarmCreator.AlarmCreator
import com.example.taskapp.utils.alarmCreator.AlarmCreatorImpl
import com.example.taskapp.utils.notification.NotificationIntentFactoryImpl


class UpdateRemindersWorker constructor(
    appContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters) {


    //todo  inject
    private val alarmCreator: AlarmCreator = AlarmCreatorImpl(
        applicationContext,
        NotificationIntentFactoryImpl(context = appContext)
    )

    //    @Inject
    private val repository: TaskRepository = TaskRepositoryImpl(
        AppDataBase.getInstance(applicationContext).taskDao()
    )

    private val taskUpdater: TaskUpdater = TaskUpdaterImpl(repository, alarmCreator)


    @Suppress("UNCHECKED_CAST")
    override suspend fun doWork(): Result {
        return when(taskUpdater.filterAndUpdateTasks()){
            is com.example.taskapp.data.Result.Success -> Result.success()
            is  com.example.taskapp.data.Result.Error -> Result.retry()
        }

    }


    companion object {
        private const val TASK_KEY = "task"
        const val WORK_NAME = "UpdateNotificationDatesWorker"
    }

}

