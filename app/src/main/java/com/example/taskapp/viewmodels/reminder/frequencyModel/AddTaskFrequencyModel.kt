package com.example.taskapp.viewmodels.reminder.frequencyModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskapp.viewmodels.reminder.DayOfWeekValue
import com.example.taskapp.viewmodels.reminder.ReminderFrequencyState
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import javax.inject.Inject

class AddTaskFrequencyModel @Inject constructor() : FrequencyModel() {



    override var frequencyState: ReminderFrequencyState = ReminderFrequencyState.Daily()
        private set


    override var currentWeekDays: Set<DayOfWeekValue> = setOf()
        private set


    override fun setDailyFrequency(freq: Int) {
        frequencyState = ReminderFrequencyState.Daily(freq)
        currentDailyFrequency = freq
    }

    override fun setDaysOfWeekFrequency(daysOfWeek: Set<DayOfWeekValue>?) {
        var days = daysOfWeek
        if (days == null) {
            days = checkedDays
        }
        currentWeekDays = (days)

        frequencyState = ReminderFrequencyState.WeekDays(days)
    }


}
