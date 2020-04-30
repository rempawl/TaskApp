package com.example.taskapp.viewmodels

import androidx.databinding.ObservableField
import com.example.taskapp.MyApp.Companion.TODAY
import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.scheduler.SchedulerProvider
import com.example.taskapp.viewmodels.addTask.TaskDetailsModel
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import com.example.taskapp.viewmodels.reminder.durationModel.EditTaskDurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.EditTaskFrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.EditTaskNotificationModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Single

class EditTaskViewModel @AssistedInject constructor(
    val taskDetailsModel: TaskDetailsModel,
    @Assisted task: DefaultTask,
    private val taskRepository: TaskRepositoryInterface,
    durationModelFactory: EditTaskDurationModel.Factory,
    frequencyModelFactory: EditTaskFrequencyModel.Factory,
    defaultNotificationModelFactory: EditTaskNotificationModel.Factory,
    schedulerProvider: SchedulerProvider
) : ReminderViewModel(
    task,
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

    val isReminderSwitchChecked = ObservableField<Boolean>(task.reminder != null)

    init {
        taskDetailsModel.taskDescription = task.description
    }

    //todo setting alarm for edited task
    suspend fun saveEditedTask() {
        saveTask(isReminderSwitchChecked.get() as Boolean)
    }

    override suspend fun addTask(task: DefaultTask): Single<Long> {
        taskRepository.updateTask(task)
        return Single.just(-1)
    }

    override fun createTask(reminder: Reminder?): DefaultTask {
        return task.copy(
            description = taskDetailsModel.taskDescription,
            reminder = reminder
        )
    }

}




