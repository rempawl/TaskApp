package com.example.taskapp.fragments.reminder

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.taskapp.viewmodels.reminder.durationModel.DurationModel
import org.threeten.bp.LocalDate

class EndDatePickerFragment(
    private val modelDefault: DurationModel
) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val endDate = modelDefault.currentEndDate
        val year =endDate.year
        val day = endDate.dayOfMonth
        val month = endDate.monthValue-1  //java util time
        return DatePickerDialog(requireContext(), this, year, month, day)
    }


    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        val date = LocalDate.of(year,month+1,day)
        modelDefault.setEndDateDurationState(endDate = date)
    }

}