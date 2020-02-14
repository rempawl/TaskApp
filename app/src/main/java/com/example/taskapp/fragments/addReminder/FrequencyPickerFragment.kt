package com.example.taskapp.fragments.addReminder

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.example.taskapp.R
import com.example.taskapp.viewmodels.addReminder.AddReminderViewModel

class FrequencyPickerFragment(private val viewModel: AddReminderViewModel) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        retainInstance = true

        val numberPicker = NumberPicker(requireContext()).apply {
            minValue = 1
            maxValue = 100
            value = viewModel.frequencyModel.currentDailyFrequency
        }
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.frequency))
            .setView(numberPicker)
            .setPositiveButton(R.string.ok) { _, _ -> setDailyFrequency(numberPicker.value) }
            .setNegativeButton(R.string.cancel) { _, _ ->  }
            .create()
    }

    private fun setDailyFrequency(frequency: Int) {
        viewModel.frequencyModel.setDailyFrequency(frequency)
    }

}

