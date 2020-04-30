package com.example.taskapp.viewmodels.reminder.durationModel

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.example.taskapp.viewmodels.reminder.ReminderDurationState
import org.threeten.bp.LocalDate
import javax.inject.Inject

class AddTaskDurationModel @Inject constructor() : DurationModel() {
    override var durationState: ReminderDurationState = ReminderDurationState.NoEndDate
        private set(value) {
            field = value
            notifyPropertyChanged(BR.dateValid)
        }


    @Bindable
    override fun isDateValid(): Boolean {
        return if (durationState is ReminderDurationState.EndDate) {
            isEndDateValid()
        } else {
            true
        }
    }


    override fun isEndDateValid(date: LocalDate) =
        !date.isBefore(beginningDate) && !date.isBefore(LocalDate.now())

    override fun isBeginningDateValid(date: LocalDate): Boolean {
        val isValid = !date.isBefore(beginningDate)
        return if (durationState is ReminderDurationState.EndDate) {
            isValid && date.isBefore(currentEndDate)
        } else {
            isValid
        }
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
