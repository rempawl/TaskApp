package com.example.taskapp.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.database.entities.Task
import com.example.taskapp.repos.task.TaskRepository
import com.example.taskapp.viewmodels.addTask.TaskDetailsModel
import com.example.taskapp.viewmodels.reminder.DefaultDurationModel
import com.example.taskapp.viewmodels.reminder.DefaultNotificationModel
import com.example.taskapp.viewmodels.reminder.FrequencyModel
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class EditTaskViewModel @AssistedInject constructor(
    @Assisted val task: Task,
    private val taskRepo: TaskRepository,
    val taskDetailsModel: TaskDetailsModel,
    defaultDurationModelFactory: DefaultDurationModel.Factory,
    frequencyModelFactory: FrequencyModel.Factory,
    defaultNotificationModelFactory: DefaultNotificationModel.Factory
) :ReminderViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(task: Task): EditTaskViewModel
    }

    val durationModel: DefaultDurationModel
    val frequencyModel: FrequencyModel
    val notificationModel: DefaultNotificationModel

    init {
        val reminder = task.reminder
        if (reminder != null) {
            durationModel = defaultDurationModelFactory.create(reminder.duration, reminder.begDate)
            frequencyModel = frequencyModelFactory.create(reminder.frequency)
            notificationModel = defaultNotificationModelFactory.create(reminder.notificationTime)
        } else {
            notificationModel = defaultNotificationModelFactory.create()
            durationModel = defaultDurationModelFactory.create()
            frequencyModel = frequencyModelFactory.create()

        }
        taskDetailsModel.taskDescription = task.description
    }

    val isReminderSwitchChecked = ObservableField<Boolean>(task.reminder != null)
    private val toastText = MutableLiveData<Int>(null)
    fun getToastText(): LiveData<Int> = toastText

    private val errorCallback = ErrorCallback(durationModel,toastText)

    init {
        durationModel.endDateError.addOnPropertyChangedCallback(errorCallback)
        durationModel.begDateError.addOnPropertyChangedCallback(errorCallback)
    }

    override fun onCleared() {
        super.onCleared()
        durationModel.begDateError.removeOnPropertyChangedCallback(errorCallback)
        durationModel.endDateError.removeOnPropertyChangedCallback(errorCallback)
    }

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
            taskRepo.updateTask(edited)
        }
    }
}




