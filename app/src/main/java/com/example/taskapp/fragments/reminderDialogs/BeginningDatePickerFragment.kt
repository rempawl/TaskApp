package com.example.taskapp.fragments.reminderDialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.taskapp.viewmodels.reminder.durationModel.DurationModel
import org.threeten.bp.LocalDate

class BeginningDatePickerFragment(
    private val durationModel: DurationModel
) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        retainInstance = true
        val date = durationModel.beginningDate
        val year = date.year
        val day = date.dayOfMonth
        val month = date.monthValue - 1 //java.util.time
        return DatePickerDialog(requireContext(), this, year, month, day)

    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        durationModel.beginningDate = (LocalDate.of(year, month + 1, day))
    }

    companion object {
        const val DAY = "day"
        const val MONTH = "month"
        const val YEAR = "year"
    }


}