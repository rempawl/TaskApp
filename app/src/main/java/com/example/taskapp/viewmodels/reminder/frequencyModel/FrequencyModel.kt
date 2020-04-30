package com.example.taskapp.viewmodels.reminder.frequencyModel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.taskapp.BR
import com.example.taskapp.database.entities.Frequency
import com.example.taskapp.viewmodels.reminder.DayOfWeekValue
import com.example.taskapp.viewmodels.reminder.ReminderFrequencyState
import org.threeten.bp.LocalDate


abstract class FrequencyModel :
    BaseObservable() {

    abstract val frequencyState: ReminderFrequencyState

    @Bindable
     var currentDailyFrequency = ReminderFrequencyState.INITIAL_FREQUENCY
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentDailyFrequency)
        }
     var currentWeekDays: Set<DayOfWeekValue> = ReminderFrequencyState.WeekDays().daysOfWeek


//    abstract var currentDailyFrequency: Int


//    abstract var currentWeekDays: Set<DayOfWeekValue>

    abstract fun setDailyFrequency(freq: Int = currentDailyFrequency)

    abstract fun setDaysOfWeekFrequency(daysOfWeek: Set<DayOfWeekValue> = currentWeekDays)

    fun getFrequency(): Frequency = frequencyState.convertToFrequency()

    abstract fun getUpdateDate(begDate: LocalDate): LocalDate

}