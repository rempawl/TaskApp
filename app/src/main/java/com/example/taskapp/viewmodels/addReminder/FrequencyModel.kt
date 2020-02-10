package com.example.taskapp.viewmodels.addReminder

import   androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.taskapp.BR
import com.example.taskapp.database.entities.Frequency
import com.example.taskapp.fragments.addReminder.DayOfWeekHash
import com.example.taskapp.fragments.addReminder.ReminderFrequencyState
import com.example.taskapp.utils.Converters
import javax.inject.Inject

class FrequencyModel @Inject constructor(private val converter: Converters) : BaseObservable() {
    private var frequencyState: ReminderFrequencyState = ReminderFrequencyState.Daily()

    @Bindable
    var currentDailyFrequency = ReminderFrequencyState.INITIAL_FREQUENCY
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentDailyFrequency)
        }

    var currentWeekDays = ReminderFrequencyState.WeekDays().daysOfWeek

    fun setDailyFrequency(freq: Int = currentDailyFrequency) {
        frequencyState = ReminderFrequencyState.Daily(currentDailyFrequency)
        currentDailyFrequency = freq
    }

    fun setDaysOfWeekFrequency(daysOfWeek: Set<DayOfWeekHash> = currentWeekDays) {
        currentWeekDays = daysOfWeek
        frequencyState = ReminderFrequencyState.WeekDays(daysOfWeek)


    }

    fun getFrequency(): Frequency = frequencyState.convertToFrequency()

}