package com.example.taskapp.fragments.addReminder

import  android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.InputType.TYPE_CLASS_NUMBER
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.taskapp.R
import com.example.taskapp.viewmodels.addReminder.AddReminderViewModel

class DaysDurationPickerFragment(private val viewModel: AddReminderViewModel) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val durationPicker = EditText(requireContext()).apply {
            hint = getString(R.string.days_duration_hint)
            inputType = TYPE_CLASS_NUMBER
        }
        return AlertDialog.Builder(requireContext())
            .setView(durationPicker)
            .setPositiveButton(R.string.ok) { _, _ ->
                setDaysDuration(durationPicker.text.toString())
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .setTitle(R.string.duration)
            .create()
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun setDaysDuration(text: String?) {
        if (text.isNullOrBlank()) {
            showToast(getString(R.string.no_days_number))
        } else if (text[0] == '0') {
            showToast(getString(R.string.number_starts_with_0))
        } else {
            val days = text.toInt()
            if (days < Int.MAX_VALUE) {
                viewModel.durationModel.setDaysDurationState(days)
            } else {
                showToast("Really over ${Int.MAX_VALUE} days?")
            }
        }

    }


}