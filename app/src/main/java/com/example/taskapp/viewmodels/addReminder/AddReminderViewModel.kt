package com.example.taskapp.viewmodels.addReminder

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskapp.database.entities.NotificationTime
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.database.entities.Task
import com.example.taskapp.repos.task.TaskRepository
import com.example.taskapp.utils.Converters
import com.example.taskapp.viewmodels.addTask.TaskDetails
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import org.threeten.bp.LocalTime


/**
 * This [ViewModel] is shared between all addReminder package  Fragments
 * */
class AddReminderViewModel @AssistedInject constructor(
    @Assisted val taskDetails: TaskDetails,
    val durationModel: DurationModel,
    val frequencyModel: FrequencyModel,
    private val taskRepository: TaskRepository

) : ViewModel() {


    @AssistedInject.Factory
    interface Factory {
        fun create(taskDetails: TaskDetails): AddReminderViewModel
    }


    val notificationTime = ObservableField<LocalTime>(INITIAL_TIME)
    private var isNotificationTimeSet = false

    fun setNotificationTime(time: LocalTime) {
        notificationTime.set(time)
        isNotificationTimeSet = true
    }


    fun saveTaskWithReminder() {
        viewModelScope.launch {
            val temp = notificationTime.get() ?: INITIAL_TIME

            val time = NotificationTime(temp.hour, temp.minute,isNotificationTimeSet)

            val reminder = Reminder(begDate = durationModel.beginningDate,
                duration = durationModel.getDuration(),
                frequency = frequencyModel.getFrequency(),
                notificationTime = time
            )

            taskRepository.saveTask(Task(
                    name = taskDetails.name, description = taskDetails.description,
                    reminder = reminder
                )
            )

        }

    }

    companion object {
        val INITIAL_TIME: LocalTime = LocalTime.of(18, 0, 0)

    }
}
