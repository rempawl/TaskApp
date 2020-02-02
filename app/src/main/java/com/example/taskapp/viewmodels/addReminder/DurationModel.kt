package com.example.taskapp.viewmodels.addReminder

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.taskapp.BR
import com.example.taskapp.fragments.addReminder.ReminderDurationState
import org.threeten.bp.LocalDate
import javax.inject.Inject

class DurationModel @Inject constructor() : BaseObservable() {
    //todo validation of begDate and EndDate after confirmation
    //todo set date on create

    @Bindable
    var beginningDate = TODAY
        set(value) {
            field = value
            notifyPropertyChanged(BR.beginningDate)
        }

    private var durationState: ReminderDurationState = (ReminderDurationState.NoEndDate)

    fun setDaysDurationState(days: Int = currentDaysDuration) {
        durationState = ReminderDurationState.DaysDuration(days)
        currentDaysDuration = days
    }

    fun setEndDateDurationState(endDate: LocalDate = currentEndDate) {
        durationState = ReminderDurationState.EndDate(endDate)
        currentEndDate = endDate
    }

    fun setNoEndDateDurationState() {
        durationState = ReminderDurationState.NoEndDate
    }


    @Bindable
    var currentDaysDuration = 10
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentDaysDuration)
        }

    @Bindable
    var currentEndDate: LocalDate = LocalDate.ofYearDay(TODAY.year, TODAY.dayOfYear + 10)
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentEndDate)
        }

    companion object {
        val TODAY: LocalDate = LocalDate.now()

    }


}