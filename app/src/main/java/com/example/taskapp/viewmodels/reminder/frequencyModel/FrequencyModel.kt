package com.example.taskapp.viewmodels.reminder.frequencyModel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.example.taskapp.BR
import com.example.taskapp.database.entities.Frequency
import com.example.taskapp.viewmodels.reminder.DayOfWeekValue
import com.example.taskapp.viewmodels.reminder.ReminderFrequencyState
import org.threeten.bp.LocalDate


abstract class FrequencyModel : BaseObservable() {

    val isDaysOfWeekError = ObservableField(false)

    abstract val frequencyState: ReminderFrequencyState

    @Bindable
    var currentDailyFrequency = ReminderFrequencyState.INITIAL_FREQUENCY
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentDailyFrequency)
        }

    var currentWeekDays: Set<DayOfWeekValue> = ReminderFrequencyState.WeekDays().daysOfWeek

    abstract fun setDailyFrequency(freq: Int = currentDailyFrequency)

     open fun setDaysOfWeekFrequency(daysOfWeek: Set<DayOfWeekValue> = currentWeekDays){
         if(daysOfWeek.isEmpty()){
             isDaysOfWeekError.set(true)
         }
     }

    fun getFrequency(): Frequency = frequencyState.convertToFrequency()

    abstract fun getRealizationDate(begDate: LocalDate): LocalDate

}