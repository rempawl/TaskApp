package com.example.taskapp.viewmodels.reminder

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.taskapp.BR
import com.example.taskapp.database.entities.Frequency
import com.example.taskapp.utils.reminder.DayOfWeekValue
import com.example.taskapp.utils.reminder.ReminderFrequencyState
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import org.threeten.bp.LocalDate

class FrequencyModel @AssistedInject constructor(@Assisted frequency: Frequency?) :
    BaseObservable() {

    var frequencyState: ReminderFrequencyState = ReminderFrequencyState.Daily()
        private set
    private val isEdited : Boolean

    @AssistedInject.Factory
    interface Factory {
        fun create(frequency: Frequency? = null): FrequencyModel
    }


    @Bindable
    var currentDailyFrequency = ReminderFrequencyState.INITIAL_FREQUENCY
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentDailyFrequency)
        }

    var currentWeekDays = ReminderFrequencyState.WeekDays().daysOfWeek

    init {
        if (frequency != null) {
            isEdited = true
            when (val freqState = frequency.convertToFrequencyState()) {
                is ReminderFrequencyState.Daily -> setDailyFrequency(freq = freqState.frequency)
                is ReminderFrequencyState.WeekDays -> setDaysOfWeekFrequency(daysOfWeek = freqState.daysOfWeek)
            }
        } else {
            isEdited = false
        }
    }

    fun setDailyFrequency(freq: Int = currentDailyFrequency) {
        frequencyState = ReminderFrequencyState.Daily(freq)
        currentDailyFrequency = freq
    }

    fun setDaysOfWeekFrequency(daysOfWeek: Set<DayOfWeekValue> = currentWeekDays) {
        currentWeekDays = daysOfWeek
        frequencyState = ReminderFrequencyState.WeekDays(daysOfWeek)
    }

    fun getFrequency(): Frequency = frequencyState.convertToFrequency()

    fun getUpdateDate(begDate: LocalDate) = frequencyState.calculateUpdateDate(begDate, !isEdited)


}