package com.example.taskapp.viewmodels

import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import com.example.taskapp.viewmodels.reminder.durationModel.DefaultDurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.DefaultFrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.DefaultNotificationModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Single


class AddReminderViewModel @AssistedInject constructor(
    @Assisted task: DefaultTask,
    taskRepository: TaskRepositoryInterface,
    defaultDurationModelFactory: DefaultDurationModel.Factory,
    defaultNotificationModelFactory: DefaultNotificationModel.Factory,
    frequencyModelFactory: DefaultFrequencyModel.Factory
//    private val streakLocalDataSource: StreakDataSource,
) : ReminderViewModel(
    task, taskRepository, defaultDurationModelFactory,
    defaultNotificationModelFactory,
    frequencyModelFactory
) {

    @AssistedInject.Factory
    interface Factory {
        fun create(task: DefaultTask): AddReminderViewModel
    }


    override suspend fun addTask(task: DefaultTask): Single<Long> {
        return taskRepository.saveTask(task)
    }

    override  fun createTask(reminder: Reminder?): DefaultTask {
        return task.copy(reminder = reminder)

    }


}


