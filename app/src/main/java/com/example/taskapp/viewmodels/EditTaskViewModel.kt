package com.example.taskapp.viewmodels

import com.example.taskapp.MyApp.Companion.TODAY
import com.example.taskapp.database.entities.task.DefaultTask
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.providers.SchedulerProvider
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import com.example.taskapp.viewmodels.reminder.durationModel.EditTaskDurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.EditTaskFrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.EditTaskNotificationModel
import com.example.taskapp.viewmodels.taskDetails.TaskDetailsModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Single

class EditTaskViewModel @AssistedInject constructor(
    taskDetailsModel: TaskDetailsModel,
    @Assisted task: DefaultTask,
    private val taskRepository: TaskRepositoryInterface,
    durationModelFactory: EditTaskDurationModel.Factory,
    frequencyModelFactory: EditTaskFrequencyModel.Factory,
    defaultNotificationModelFactory: EditTaskNotificationModel.Factory,
    schedulerProvider: SchedulerProvider
) : ReminderViewModel(
    taskDetailsModel = taskDetailsModel,
    task = task,
    durationModel = durationModelFactory.create(
        task.reminder?.duration,
        task.reminder?.begDate ?: TODAY
    ),
    frequencyModel = frequencyModelFactory.create(task.reminder?.frequency),
    notificationModel = defaultNotificationModelFactory.create(notificationTime = task.reminder?.notificationTime),
    schedulerProvider = schedulerProvider

) {

    @AssistedInject.Factory
    interface Factory {
        fun create(task: DefaultTask): EditTaskViewModel
    }


    init {
        taskDetailsModel.apply{
            taskDescription = task.description
            taskName = task.name
        }
    }


    override suspend fun addTask(task: DefaultTask): Single<Long> {
        taskRepository.updateTask(task)
        return Single.just(1)
    }


}




