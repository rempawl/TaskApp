package com.example.taskapp.fragments.addReminder

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.taskapp.viewmodels.addReminder.AddReminderViewModel
import org.threeten.bp.LocalTime

class NotificationTimePickerFragment(private val viewModel: AddReminderViewModel) :
    DialogFragment(),
    TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val time = viewModel.notificationModel.notificationTime
        val hour = time.hour
        val minute = time.minute
        return TimePickerDialog(requireContext(), this, hour, minute, true)
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
      viewModel.notificationModel.setNotificationTime(LocalTime.of(hour, minute))
    }


}