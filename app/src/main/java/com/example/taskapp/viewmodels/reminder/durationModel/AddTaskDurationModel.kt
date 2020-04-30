package com.example.taskapp.viewmodels.reminder.durationModel

import androidx.databinding.library.baseAdapters.BR
import com.example.taskapp.viewmodels.reminder.ReminderDurationState
import org.threeten.bp.LocalDate
import javax.inject.Inject

class AddTaskDurationModel @Inject constructor() : DurationModel() {
    override var durationState: ReminderDurationState = ReminderDurationState.NoEndDate
        private set(value) {
            field = value
            notifyPropertyChanged(BR.datesValid)

        }



    override fun setNoEndDateDurationState() {
        durationState = ReminderDurationState.NoEndDate
    }


    override fun setDaysDurationState(days: Int) {
        durationState = ReminderDurationState.DaysDuration(days)
        currentDaysDuration = days
    }


    override fun setEndDateDurationState(endDate: LocalDate) {
        durationState = ReminderDurationState.EndDate(endDate)
        currentEndDate = endDate
    }


}
