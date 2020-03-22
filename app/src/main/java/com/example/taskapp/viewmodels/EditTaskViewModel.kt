package com.example.taskapp.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.database.entities.Task
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.viewmodels.addTask.TaskDetailsModel
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import com.example.taskapp.viewmodels.reminder.durationModel.DefaultDurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.DefaultFrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.DefaultNotificationModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class EditTaskViewModel @AssistedInject constructor(
    val taskDetailsModel: TaskDetailsModel,
    @Assisted  task: Task,
    taskRepository: TaskRepositoryInterface,
    defaultDurationModelFactory: DefaultDurationModel.Factory,
    frequencyModelFactory: DefaultFrequencyModel.Factory,
    defaultNotificationModelFactory: DefaultNotificationModel.Factory
) :ReminderViewModel(
    task, taskRepository, defaultDurationModelFactory,
    defaultNotificationModelFactory,
    frequencyModelFactory

) {

    @AssistedInject.Factory
    interface Factory {
        fun create(task: Task): EditTaskViewModel
    }

    val isReminderSwitchChecked = ObservableField<Boolean>(task.reminder != null)
    init{
        taskDetailsModel.taskDescription = task.description
    }

    //todo setting alarm for edited task
    fun saveEditedTask() {
        viewModelScope.launch {
            var reminder: Reminder? = null
            if (isReminderSwitchChecked.get() == true) {
                reminder = Reminder(
                    begDate = durationModel.beginningDate,
                    frequency = frequencyModel.getFrequency(),
                    duration = durationModel.getDuration(),
                    notificationTime = notificationModel.getNotificationTime(),
                    expirationDate = durationModel.getExpirationDate(),
                    realizationDate = frequencyModel.getUpdateDate(durationModel.beginningDate)
                )
            }
            val edited = task.copy(
                description = taskDetailsModel.taskDescription,
                reminder = reminder
            )
            taskRepository.updateTask(edited)
        }
    }
}




