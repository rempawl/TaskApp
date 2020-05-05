package com.example.taskapp.fragments.reminderDialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.taskapp.R
import com.example.taskapp.viewmodels.reminder.DayOfWeekValue
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel
import com.google.android.material.checkbox.MaterialCheckBox
import org.threeten.bp.DayOfWeek

class WeekDayPickerFragment(private val model: FrequencyModel) : DialogFragment() {

    private val checkedDays: MutableSet<DayOfWeekValue> by lazy {
        model.currentWeekDays.toMutableSet()
    }

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
        var i = 0
        val days = DayOfWeek.values()

        //iterating over names of days, setting id to matching [DayOfWeek] hashcode
        resources.getStringArray(R.array.week_days_list).forEach { dayText ->
            val box = MaterialCheckBox(requireContext()).apply {
                setUpCheckBox(dayText = dayText, dayValue = days[i].value, i = i)
            }
            layout.addView(box)
            i++
        }
        return layout
    }

    private fun MaterialCheckBox.setUpCheckBox(dayText: String?, dayValue: Int, i: Int) {

        gravity = Gravity.CENTER_HORIZONTAL
        text = dayText
        id = dayValue
        isChecked = if (checkedDays.isEmpty() && i == 1) {
            true
        } else {
            checkedDays.contains(dayValue)
        }
        setOnCheckedChangeListener { box, isChecked -> onCheckedChange(isChecked, box) }
    }

    private fun onCheckedChange(isChecked: Boolean, box: CompoundButton) {
        if (isChecked) {
            checkedDays.add(box.id)
        } else {
            if (checkedDays.size == 1) {
                Toast.makeText(context,
                    getString(R.string.days_of_week_error),
                    Toast.LENGTH_SHORT
                ).show()
                box.isChecked = true
            } else {
                checkedDays.remove(box.id)
            }
        }
    }

    private fun setWeekDaysFrequency() {
        model.setDaysOfWeekFrequency(checkedDays)
    }
}
