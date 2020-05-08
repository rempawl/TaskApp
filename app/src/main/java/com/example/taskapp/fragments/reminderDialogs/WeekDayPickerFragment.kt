package com.example.taskapp.fragments.reminderDialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.CompoundButton
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.taskapp.MainActivity
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
        Log.d(MainActivity.TAG,"new one")
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

        setOnCheckedChangeListener(onCheckedChangeListener)
        gravity = Gravity.CENTER_HORIZONTAL
        text = dayText
        id = dayValue
        val checkedDays = model.currentWeekDays

        Log.d(MainActivity.TAG,"$checkedDays")

        isChecked = if (checkedDays.isEmpty() && i == 1) {
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
