package com.example.taskapp.fragments.reminderDialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.InputType.TYPE_CLASS_NUMBER
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.taskapp.R
import com.example.taskapp.viewmodels.reminder.durationModel.DurationModel

class DaysDurationPickerFragment(private val model: DurationModel) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        retainInstance = true

        val durationPicker = createDurationPicker()

        return AlertDialog.Builder(requireContext())
            .setView(durationPicker)
            .setPositiveButton(R.string.ok) { _, _ ->
                setDaysDuration(durationPicker.text.toString())
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .setTitle(R.string.duration)
            .create()
    }

    private fun createDurationPicker(): EditText {
        return EditText(requireContext()).apply {
            hint = getString(R.string.days_duration_hint)
            inputType = TYPE_CLASS_NUMBER
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun setDaysDuration(text: String?) {
        if (text.isNullOrBlank()) {
            showToast(getString(R.string.error_blank_input))
        } else if (text[0] == '0') {
            showToast(getString(R.string.error_number_starts_with_0))
        } else {
            val days = text.toInt()
            if (days < Int.MAX_VALUE) {
                model.setDaysDurationState(days)
            } else {
                showToast("Really over ${Int.MAX_VALUE} days?")
            }
        }

    }


}