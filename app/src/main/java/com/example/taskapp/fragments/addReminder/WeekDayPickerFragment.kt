package com.example.taskapp.fragments.addReminder

import  android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.taskapp.R
import com.example.taskapp.viewmodels.addReminder.AddReminderViewModel
import com.google.android.material.checkbox.MaterialCheckBox
import org.threeten.bp.DayOfWeek

class WeekDayPickerFragment(private val viewModel: AddReminderViewModel) : DialogFragment() {

    private val checkedDays: MutableSet<DayOfWeekValue> by lazy {
        viewModel.frequencyModel.currentWeekDays.toMutableSet()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

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
        var i = 0
        val days = DayOfWeek.values()
        /**
         *iterating over names of days, setting id to matching [DayOfWeek] hashcode,
         */
        resources.getStringArray(R.array.week_days_list).forEach { day ->
            val box = MaterialCheckBox(requireContext()).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                text = day
                val dayValue = days[i].value
                id = dayValue
                isChecked = checkedDays.contains(dayValue)
                setOnCheckedChangeListener { box, isChecked ->
                    if (isChecked) {
                        checkedDays.add(box.id)
                    } else {
                        checkedDays.remove(box.id)
                    }
                }
            }
            layout.addView(box)
            i++
        }
        return layout
    }

    private fun setWeekDaysFrequency() {
        viewModel.frequencyModel.setDaysOfWeekFrequency(checkedDays)
    }
}
