package com.example.taskapp.fragments.addReminder

import   android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.taskapp.viewmodels.addReminder.AddReminderViewModel
import org.threeten.bp.LocalDate

class BeginningDatePickerFragment(
    private val viewModel: AddReminderViewModel
) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = viewModel.durationModel.beginningDate
        val year = date.year
        val day = date.dayOfMonth
        val month = date.monthValue - 1 //java.util.time
        return DatePickerDialog(requireContext(), this, year, month, day)

    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        viewModel.durationModel.beginningDate = (LocalDate.of(year, month + 1, day))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(DAY, (dialog as DatePickerDialog).datePicker.dayOfMonth)
        outState.putInt(MONTH, (dialog as DatePickerDialog).datePicker.month)
        outState.putInt(YEAR, (dialog as DatePickerDialog).datePicker.year)
    }

    companion object {
        const val DAY = "day"
        const val MONTH = "month"
        const val YEAR = "year"
    }


}