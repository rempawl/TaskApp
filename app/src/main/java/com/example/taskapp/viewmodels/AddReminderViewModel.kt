package com.example.taskapp.viewmodels

import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.scheduler.SchedulerProvider
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import com.example.taskapp.viewmodels.reminder.durationModel.DurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.NotificationModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Single


class AddReminderViewModel @AssistedInject constructor(
    @Assisted task: DefaultTask,
    private val taskRepository: TaskRepositoryInterface,
    durationModel: DurationModel,
    notificationModel: NotificationModel,
    frequencyModel: FrequencyModel,
    schedulerProvider: SchedulerProvider
//    private val streakLocalDataSource: StreakDataSource,
) : ReminderViewModel(
    task = task,
    durationModel = durationModel,
    notificationModel = notificationModel,
    frequencyModel = frequencyModel,
    schedulerProvider = schedulerProvider
) {

    @AssistedInject.Factory
    interface Factory {
        fun create(task: DefaultTask): AddReminderViewModel
    }


    override suspend fun addTask(task: DefaultTask): Single<Long> {
        return taskRepository.saveTask(task)
    }

    override fun createTask(reminder: Reminder?): DefaultTask {
        return task.copy(reminder = reminder)

    }


}


