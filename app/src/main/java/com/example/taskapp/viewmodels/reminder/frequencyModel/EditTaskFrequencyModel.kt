package com.example.taskapp.viewmodels.reminder.frequencyModel

import com.example.taskapp.database.entities.reminder.Frequency
import com.example.taskapp.viewmodels.reminder.DayOfWeekValue
import com.example.taskapp.viewmodels.reminder.ReminderFrequencyState
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class EditTaskFrequencyModel @AssistedInject constructor(@Assisted frequency: Frequency? ):
    FrequencyModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(frequency: Frequency? = null): FrequencyModel
    }

    override var frequencyState: ReminderFrequencyState = ReminderFrequencyState.Daily()
        private set

    override var currentWeekDays: Set<DayOfWeekValue> = setOf()
        private set


    private val isEdited: Boolean

    init {
        if (frequency != null) {
            isEdited = true
            when (val freqState = frequency.convertToFrequencyState()) {
                is ReminderFrequencyState.Daily -> {
                    setDailyFrequency(freq = freqState.frequency)
//                    initCallback(ReminderViewModel.DAILY_FREQ_RADIO_ID)
                }
                is ReminderFrequencyState.WeekDays -> {
                    initDaysOfWeekState(freqState)
//                    initCallback(ReminderViewModel.DAYS_OF_WEEK_FREQ_RADIO_ID)

                }
            }
        } else {
            isEdited = false
        }
    }

    private fun initDaysOfWeekState(freqState: ReminderFrequencyState.WeekDays) {
        setDaysOfWeekFrequency(daysOfWeek = freqState.daysOfWeek)
        checkedDays.apply {
            clear()
            addAll(freqState.daysOfWeek)
        }
    }

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
