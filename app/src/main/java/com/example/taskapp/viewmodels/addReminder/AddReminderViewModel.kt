package com.example.taskapp.viewmodels.addReminder

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskapp.MainActivity
import com.example.taskapp.fragments.addReminder.ReminderDurationState
import com.example.taskapp.fragments.addReminder.ReminderFrequencyState
import com.example.taskapp.viewmodels.addTask.TaskDetails
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime


/**
 * This [ViewModel] is shared between all addReminder package  Fragments
 * */
class AddReminderViewModel @AssistedInject constructor(
    @Assisted val taskDetails: TaskDetails,
    val durationModel: DurationModel,
    val frequencyModel: FrequencyModel
) : ViewModel() {
    init {
        Log.d(MainActivity.TAG, "New ViewModel")
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(taskDetails: TaskDetails) : AddReminderViewModel
    }
    //todo notificationTIme validation
    //todo errors on invalid Date
     val notificationTime = ObservableField<LocalTime>(INITIAL_TIME)
    var isNotificationTimeSet = false


    companion object {
        val INITIAL_TIME : LocalTime = LocalTime.of(18, 0, 0)

    }
}
