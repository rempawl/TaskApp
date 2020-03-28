package com.example.taskapp.fragments.reminder

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.taskapp.viewmodels.reminder.notificationModel.NotificationModel
import org.threeten.bp.LocalTime

class NotificationTimePickerFragment(private val notificationModel: NotificationModel) :
    DialogFragment(),
    TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        retainInstance = true
        val time = notificationModel.notificationTime
        val hour = time.hour
        val minute = time.minute

        return TimePickerDialog(requireContext(), this, hour, minute, true)
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
      notificationModel.notificationTime = (LocalTime.of(hour, minute))
    }


}