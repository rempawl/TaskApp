package com.example.taskapp.viewmodels.reminder.frequencyModel

import com.example.taskapp.database.entities.Frequency
import com.example.taskapp.viewmodels.reminder.DayOfWeekValue
import com.example.taskapp.viewmodels.reminder.ReminderFrequencyState
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import org.threeten.bp.LocalDate

class EditTaskFrequencyModel @AssistedInject constructor(@Assisted frequency: Frequency?) :
    FrequencyModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(frequency: Frequency? = null): FrequencyModel
    }

    override var frequencyState: ReminderFrequencyState = ReminderFrequencyState.Daily()
        private set

    private val isEdited: Boolean




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
        isBeginning = !isEdited
    )


}
