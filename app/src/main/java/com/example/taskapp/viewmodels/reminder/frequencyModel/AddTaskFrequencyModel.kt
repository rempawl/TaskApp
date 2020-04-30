package com.example.taskapp.viewmodels.reminder.frequencyModel

import com.example.taskapp.viewmodels.reminder.DayOfWeekValue
import com.example.taskapp.viewmodels.reminder.ReminderFrequencyState
import org.threeten.bp.LocalDate
import javax.inject.Inject

class AddTaskFrequencyModel @Inject constructor() : FrequencyModel(){


    override var frequencyState: ReminderFrequencyState = ReminderFrequencyState.Daily()
        private set



    override fun setDailyFrequency(freq: Int ) {
        frequencyState = ReminderFrequencyState.Daily(freq)
        currentDailyFrequency = freq
    }


    override fun setDaysOfWeekFrequency(daysOfWeek: Set<DayOfWeekValue> ) {
        currentWeekDays = daysOfWeek
        frequencyState = ReminderFrequencyState.WeekDays(daysOfWeek)
    }


    override fun getUpdateDate(begDate: LocalDate) = frequencyState.calculateRealizationDate(
        lastRealizationDate = begDate,
        isBeginning = true
    )


}
