package com.example.taskapp.viewmodels.reminder.frequencyModel

import android.widget.CompoundButton
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.taskapp.BR
import com.example.taskapp.database.entities.Frequency
import com.example.taskapp.viewmodels.reminder.DayOfWeekValue
import com.example.taskapp.viewmodels.reminder.ReminderFrequencyState
import org.threeten.bp.LocalDate


abstract class FrequencyModel : BaseObservable() {


    abstract val onCheckedListener : CompoundButton.OnCheckedChangeListener

    abstract val frequencyState: ReminderFrequencyState

    @Bindable
    var currentDailyFrequency = ReminderFrequencyState.INITIAL_FREQUENCY
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentDailyFrequency)
        }


    abstract val currentWeekDays: Set<DayOfWeekValue>


    abstract fun setDailyFrequency(freq: Int = currentDailyFrequency)

    abstract fun setDaysOfWeekFrequency(daysOfWeek: Set<DayOfWeekValue> = emptySet())

    fun getFrequency(): Frequency = frequencyState.convertToFrequency()

    abstract fun getRealizationDate(begDate: LocalDate): LocalDate

}