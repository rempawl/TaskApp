package com.example.taskapp.fragments.addReminder

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.taskapp.viewmodels.addReminder.AddReminderViewModel

class NotificationTimePickerFragment(private val viewModel: AddReminderViewModel) : DialogFragment(),
TimePickerDialog.OnTimeSetListener{

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val time = viewModel.currentNotificationTime
        val hour  = time.hour
        val minute = time.minute
        return TimePickerDialog(requireContext(),this,hour,minute,true)

//        return AlertDialog.Builder(requireContext())
//            .setView()
//            .setTitle(R.string.set_notification_time)
//            .setPositiveButton(R.string.ok){_,_ ->}
//            .setNegativeButton(R.string.cancel){ _,_ ->}
//            .create()
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {

    }


}