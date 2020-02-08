package com.example.taskapp.viewmodels.addReminder

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.taskapp.BR
import com.example.taskapp.utils.Converters
import com.example.taskapp.database.entities.Frequency
import com.example.taskapp.fragments.addReminder.DayOfWeekHash
import com.example.taskapp.fragments.addReminder.ReminderFrequencyState
import javax.inject.Inject

class FrequencyModel @Inject constructor(): BaseObservable(){
    private var frequencyState :ReminderFrequencyState =  ReminderFrequencyState.Daily()

    @Bindable
    var currentDailyFrequency = ReminderFrequencyState.INITIAL_FREQUENCY
    set(value) {
        field = value
        notifyPropertyChanged(BR.currentDailyFrequency)
    }

    var currentWeekDays = ReminderFrequencyState.WeekDays().days

    fun setDailyFrequency(freq : Int = currentDailyFrequency){
        frequencyState = ReminderFrequencyState.Daily(currentDailyFrequency)
        currentDailyFrequency = freq
    }

    fun setDaysOfWeekFrequency(daysOfWeek : Set<DayOfWeekHash> = currentWeekDays){
        currentWeekDays = daysOfWeek
        frequencyState = ReminderFrequencyState.WeekDays(daysOfWeek)


    }

    fun getFrequency() : Frequency{
        return when(frequencyState){
            is ReminderFrequencyState.Daily ->{
                Frequency(isDaily = true,frequency = currentDailyFrequency)
            }
            is ReminderFrequencyState.WeekDays -> {
                Frequency(isDaily = false,frequency = Converters().daysOfWeekToInt(currentWeekDays))
            }
        }
    }

}