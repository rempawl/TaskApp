package com.example.taskapp.fragments.addReminder

import  android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.taskapp.fragments.addReminder.ReminderDurationState
import com.example.taskapp.viewmodels.AddReminderViewModel
import org.threeten.bp.LocalDate

class EndDatePickerFragment(
    private val viewModel: AddReminderViewModel
) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val endDate = viewModel.currentEndDate
        val year =endDate.year
        val day = endDate.dayOfMonth
        val month = endDate.monthValue-1  //java util time
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }



    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        val date = LocalDate.of(year,month+1,day)
        viewModel.currentEndDate =  date
        viewModel.setDurationState(ReminderDurationState.EndDate(date))
    }

}