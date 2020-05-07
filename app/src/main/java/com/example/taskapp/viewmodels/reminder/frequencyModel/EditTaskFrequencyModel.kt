package com.example.taskapp.viewmodels.reminder.frequencyModel

import android.widget.CompoundButton
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

    override val onCheckedListener: CompoundButton.OnCheckedChangeListener
        get() = CompoundButton.OnCheckedChangeListener { btn, isChecked ->
            onCheckedChange(isChecked,btn)
        }

    override var frequencyState: ReminderFrequencyState = ReminderFrequencyState.Daily()
        private set

    private val _currentWeekDays: MutableSet<DayOfWeekValue> = mutableSetOf()
    override val currentWeekDays: Set<DayOfWeekValue>
        get() = _currentWeekDays

    private val isEdited: Boolean

    init {
        if (frequency != null) {
            isEdited = true
            when (val freqState = frequency.convertToFrequencyState()) {
                is ReminderFrequencyState.Daily -> setDailyFrequency(freq = freqState.frequency)
                is ReminderFrequencyState.WeekDays -> setDaysOfWeekFrequency(freqState.daysOfWeek)
            }
        } else {
            isEdited = false
        }
    }

    override fun setDailyFrequency(freq: Int) {
        frequencyState = ReminderFrequencyState.Daily(freq)
        currentDailyFrequency = freq
    }

    private fun onCheckedChange(isChecked: Boolean, box: CompoundButton) {
        if (isChecked) {
            _currentWeekDays.add(box.id)
        } else {
            if (_currentWeekDays.size == 1) {
                //todo error
                box.isChecked = true
            } else {
                _currentWeekDays.remove(box.id)
            }
        }
    }


    override fun setDaysOfWeekFrequency(daysOfWeek: Set<DayOfWeekValue>) {
        _currentWeekDays.addAll(daysOfWeek)
        frequencyState = ReminderFrequencyState.WeekDays(currentWeekDays)
    }

    override fun getRealizationDate(begDate: LocalDate) = frequencyState.calculateRealizationDate(
        lastRealizationDate = begDate,
        isBeginning = !isEdited
    )


}
