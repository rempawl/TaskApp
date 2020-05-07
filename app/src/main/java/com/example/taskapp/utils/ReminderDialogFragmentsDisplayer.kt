package com.example.taskapp.utils

import androidx.fragment.app.FragmentManager
import com.example.taskapp.fragments.AddTaskFragment
import com.example.taskapp.fragments.reminderDialogs.*
import com.example.taskapp.viewmodels.reminder.durationModel.DurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.NotificationModel

object ReminderDialogFragmentsDisplayer {


    fun showNotificationPickerDialog(
        defaultNotificationModel: NotificationModel, childFragmentManager: FragmentManager
    ) {
        NotificationTimePickerFragment(defaultNotificationModel)
            .show(childFragmentManager, "Notification dialog tag")

    }


    fun showDurationDaysPickerDialog(
        defaultDurationModel: DurationModel, childFragmentManager: FragmentManager
    ) {
        DaysDurationPickerFragment(defaultDurationModel)
            .show(childFragmentManager, "days duration dialog")
    }


    fun showDaysOfWeekPickerDialog(
        frequencyModel: FrequencyModel, childFragmentManager: FragmentManager
    ) {
        WeekDayPickerFragment(frequencyModel, frequencyModel.onCheckedListener)
            .show(childFragmentManager, "weekday picker dialog")
    }


    fun showFrequencyPickerDialog(
        frequencyModel: FrequencyModel, childFragmentManager: FragmentManager
    ) {
        FrequencyPickerFragment(frequencyModel)
            .show(childFragmentManager, "FREQUENCY PICKER DIALOG")
    }


    fun showEndDatePickerDialog(
        defaultDurationModel: DurationModel, childFragmentManager: FragmentManager
    ) {
        EndDatePickerFragment(defaultDurationModel)
            .show(childFragmentManager, AddTaskFragment.END_DATE_DIALOG_TAG)
    }

    fun showBegDatePickerDialog(
        defaultDurationModel: DurationModel, childFragmentManager: FragmentManager
    ) {
        BeginningDatePickerFragment(defaultDurationModel)
            .show(childFragmentManager, AddTaskFragment.BEGINNING_DATE_DIALOG_TAG)
    }
}