package com.example.taskapp.viewmodels.reminder.frequencyModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskapp.data.reminder.Frequency
import com.example.taskapp.viewmodels.reminder.DayOfWeekValue
import com.example.taskapp.viewmodels.reminder.ReminderFrequencyState
import com.example.taskapp.viewmodels.reminder.ReminderViewModel.FrequencyRadioState
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class EditTaskFrequencyModel @AssistedInject constructor(@Assisted frequency: Frequency?) :
    FrequencyModel() {


    @AssistedInject.Factory
    interface Factory {
        fun create(frequency: Frequency? = null): FrequencyModel
    }

    override var frequencyState: ReminderFrequencyState = ReminderFrequencyState.Daily()
        private set

    private val _initFreqState = MutableLiveData<FrequencyRadioState>()
    override val initFreqState: LiveData<FrequencyRadioState>
        get() = _initFreqState

    override var currentWeekDays: Set<DayOfWeekValue> = setOf()
        private set

    private val isEdited: Boolean

    init {
        if (frequency != null) {
            isEdited = true
            _initFreqState.value = when (val freqState = frequency.convertToFrequencyState()) {
                is ReminderFrequencyState.Daily -> {
                    setDailyFrequency(freq = freqState.frequency)
                    FrequencyRadioState.DailyFreqRadioState()
                }
                is ReminderFrequencyState.WeekDays -> {
                    initDaysOfWeekState(freqState)
                    FrequencyRadioState.DaysOfWeekRadio()
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
