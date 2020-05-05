package com.example.taskapp.fragments.reminderDialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.example.taskapp.R
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel

class FrequencyPickerFragment(private val model: FrequencyModel) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        retainInstance = true

        val numberPicker = crateNumberPicker()
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.frequency))
            .setView(numberPicker)
            .setPositiveButton(R.string.ok) { _, _ -> setDailyFrequency(numberPicker.value) }
            .setNegativeButton(R.string.cancel) { _, _ ->  }
            .create()
    }

    private fun crateNumberPicker(): NumberPicker {
        return NumberPicker(requireContext()).apply {
            minValue = 1
            maxValue = 100
            value = model.currentDailyFrequency
        }
    }

    private fun setDailyFrequency(frequency: Int) {
        model.setDailyFrequency(frequency)
    }

}

