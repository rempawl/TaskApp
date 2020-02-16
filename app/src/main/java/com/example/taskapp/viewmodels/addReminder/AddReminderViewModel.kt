package com.example.taskapp.viewmodels.addReminder

import androidx.lifecycle.viewModelScope
import com.example.taskapp.database.entities.NotificationTime
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.database.entities.Task
import com.example.taskapp.repos.task.TaskRepository
import com.example.taskapp.viewmodels.ReminderViewModel
import com.example.taskapp.viewmodels.addTask.TaskDetails
import com.example.taskapp.viewmodels.reminder.DurationModel
import com.example.taskapp.viewmodels.reminder.FrequencyModel
import com.example.taskapp.viewmodels.reminder.NotificationModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch


class AddReminderViewModel @AssistedInject constructor(
    @Assisted val taskDetails: TaskDetails,
    private val taskRepository: TaskRepository,
    durationModelFactory: DurationModel.Factory,
    notificationModelFactory: NotificationModel.Factory,
    frequencyModelFactory: FrequencyModel.Factory
) : ReminderViewModel(durationModelFactory, frequencyModelFactory, notificationModelFactory) {

    @AssistedInject.Factory
    interface Factory {
        fun create(taskDetails: TaskDetails): AddReminderViewModel
    }

    fun saveTaskWithReminder() {
        viewModelScope.launch {
            val temp = notificationModel.notificationTime
            val time =
                NotificationTime(temp.hour, temp.minute, notificationModel.isNotificationTimeSet)

            val reminder = Reminder(
                begDate = durationModel.beginningDate,
                duration = durationModel.getDuration(),
                frequency = frequencyModel.getFrequency(),
                notificationTime = time,
                updateDate = frequencyModel.getUpdateDate(durationModel.beginningDate),
                expirationDate = durationModel.getExpirationDate()
            )

            taskRepository.saveTask(
                Task(
                    name = taskDetails.name, description = taskDetails.description,
                    reminder = reminder
                )
            )
        }
    }


    companion object
}

