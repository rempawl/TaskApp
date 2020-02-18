package com.example.taskapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.taskapp.viewmodels.reminder.DurationModel
import com.example.taskapp.viewmodels.reminder.FrequencyModel
import com.example.taskapp.viewmodels.reminder.NotificationModel
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
open class ReminderViewModel @Inject constructor(
    protected val durationModelFactory: DurationModel.Factory,
    protected val frequencyModelFactory: FrequencyModel.Factory,
    private val notificationModelFactory: NotificationModel.Factory
) : ViewModel()
