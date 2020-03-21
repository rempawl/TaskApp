package com.example.taskapp.fragments.reminder

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.taskapp.viewmodels.reminder.DefaultNotificationModel
import org.threeten.bp.LocalTime

class NotificationTimePickerFragment(private val modelDefault: DefaultNotificationModel) :
    DialogFragment(),
    TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        retainInstance = true
        val time = modelDefault.notificationTime
        val hour = time.hour
        val minute = time.minute
        return TimePickerDialog(requireContext(), this, hour, minute, true)
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
      modelDefault.setNotificationTime(LocalTime.of(hour, minute))
    }


}