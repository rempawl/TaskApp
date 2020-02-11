package com.example.taskapp.viewmodels.addReminder

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskapp.R
import com.example.taskapp.database.entities.NotificationTime
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.database.entities.Task
import com.example.taskapp.repos.task.TaskRepository
import com.example.taskapp.viewmodels.addTask.TaskDetails
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import org.threeten.bp.LocalTime


/**
 * This [ViewModel] is shared between all addReminder package  Fragments
 * */
@Suppress("UNCHECKED_CAST")
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

    init {
        val errorCallback = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (sender != null ) {
                    val value = (sender as ObservableField<Boolean>).get()
                    if (value == true) {
                        sender.set(false)
                        when (sender) {
                            durationModel.begDateError -> {
                                toastText.value = R.string.beginning_date_error
                            }
                            durationModel.endDateError -> {
                                toastText.value = R.string.end_date_error
                            }
                        }
                    } else {
                        toastText.value = null
                    }
                }
            }
        }
        durationModel.endDateError.addOnPropertyChangedCallback(errorCallback)
        durationModel.begDateError.addOnPropertyChangedCallback(errorCallback)
    }

    private val toastText = MutableLiveData<Int>(null)
    fun getToastText(): LiveData<Int> = toastText

    val notificationTime = ObservableField<LocalTime>(INITIAL_TIME)
    private var isNotificationTimeSet = false

    fun setNotificationTime(time: LocalTime) {
        notificationTime.set(time)
        isNotificationTimeSet = true
    }


    fun saveTaskWithReminder() {
        viewModelScope.launch {
            val temp = notificationTime.get() ?: INITIAL_TIME
            val time = NotificationTime(temp.hour, temp.minute, isNotificationTimeSet)

            val reminder = Reminder(
                begDate = durationModel.beginningDate,
                duration = durationModel.getDuration(),
                frequency = frequencyModel.getFrequency(),
                notificationTime = time
            )
            taskRepository.saveTask(
                Task(
                    name = taskDetails.name, description = taskDetails.description,
                    reminder = reminder,
                    updateDate = frequencyModel.getUpdateDate(reminder.begDate),
                    expirationDate = durationModel.getExpirationDate()

                )
            )

        }
    }


    companion object {
        val INITIAL_TIME: LocalTime = LocalTime.of(18, 0, 0)

    }
}
