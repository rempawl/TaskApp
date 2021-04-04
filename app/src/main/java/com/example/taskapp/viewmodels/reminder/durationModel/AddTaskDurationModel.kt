package com.example.taskapp.viewmodels.reminder.durationModel

import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskapp.utils.DateUtils.TODAY
import com.example.taskapp.viewmodels.reminder.ReminderDurationState
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import org.threeten.bp.LocalDate
import javax.inject.Inject

class AddTaskDurationModel @Inject constructor() : DurationModel() {
    override val initDurState: LiveData<ReminderViewModel.DurationRadioState>
        get() = MutableLiveData(null)

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

    override fun isBeginningDateValid(date: LocalDate): Boolean {
        val isNotBeforeToday = !date.isBefore(   TODAY)

        return if (durationState is ReminderDurationState.EndDate) {
            isNotBeforeToday && date.isBefore(currentEndDate)
        } else {
            isNotBeforeToday
        }
    }

}
