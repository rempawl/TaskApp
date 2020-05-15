package com.example.taskapp.fragments.reminderDialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.widget.CompoundButton
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.taskapp.R
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel
import com.google.android.material.checkbox.MaterialCheckBox
import org.threeten.bp.DayOfWeek

class WeekDayPickerFragment(
    private val model: FrequencyModel,
    private val onCheckedChangeListener: CompoundButton.OnCheckedChangeListener
) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        retainInstance = true
        val layout = setupLayout()
        return AlertDialog.Builder(requireContext())
            .setView(layout)
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .setPositiveButton(R.string.ok) { _, _ -> setWeekDaysFrequency() }
            .setTitle(R.string.frequency)
            .create()
    }


    private fun setupLayout(): LinearLayout {
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
        }
        val days = DayOfWeek.values()

        val dayPairs = resources.getStringArray(R.array.week_days_list)
            .zip(days)

        for ((dayText, dayOfWeek) in dayPairs) {
            addCheckBoxToLayout(dayText, dayOfWeek, layout)
        }

//            .forEach { (dayText, dayOfWeek) ->
//                addCheckBoxToLayout(dayText, dayOfWeek, layout)
//            }


        return layout
    }

    private fun addCheckBoxToLayout(
        dayText: String?,
        dayOfWeek: DayOfWeek,
        layout: LinearLayout
    ) {
        val box = MaterialCheckBox(requireContext()).apply {
            setUpCheckBox(dayText = dayText, dayValue = dayOfWeek.value)
        }
        layout.addView(box)
    }

    private fun MaterialCheckBox.setUpCheckBox(dayText: String?, dayValue: Int) {
        setOnCheckedChangeListener(onCheckedChangeListener)

        gravity = Gravity.CENTER_HORIZONTAL
        text = dayText
        id = dayValue
        val checkedDays = model.currentWeekDays

        isChecked = if (checkedDays.isEmpty() && dayValue == 1) {
            onCheckedChangeListener.onCheckedChanged(this, true)
            true
        } else {
            checkedDays.contains(dayValue)
        }

    }

    private fun setWeekDaysFrequency() {
        model.setDaysOfWeekFrequency(null)
    }
}
