package com.example.taskapp.viewmodels

import androidx.databinding.ObservableField
import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.viewmodels.addTask.TaskDetailsModel
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import com.example.taskapp.viewmodels.reminder.durationModel.DefaultDurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.DefaultFrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.DefaultNotificationModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Single

class EditTaskViewModel @AssistedInject constructor(
    val taskDetailsModel: TaskDetailsModel,
    @Assisted task: DefaultTask,
    taskRepository: TaskRepositoryInterface,
    defaultDurationModelFactory: DefaultDurationModel.Factory,
    frequencyModelFactory: DefaultFrequencyModel.Factory,
    defaultNotificationModelFactory: DefaultNotificationModel.Factory
) : ReminderViewModel(
    task, taskRepository, defaultDurationModelFactory,
    defaultNotificationModelFactory,
    frequencyModelFactory
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




