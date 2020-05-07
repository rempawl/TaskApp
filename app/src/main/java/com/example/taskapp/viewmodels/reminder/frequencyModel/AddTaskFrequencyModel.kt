package com.example.taskapp.viewmodels.reminder.frequencyModel

import android.widget.CompoundButton
import com.example.taskapp.viewmodels.reminder.DayOfWeekValue
import com.example.taskapp.viewmodels.reminder.ReminderFrequencyState
import org.threeten.bp.LocalDate
import javax.inject.Inject

class AddTaskFrequencyModel @Inject constructor() : FrequencyModel() {


    override val onCheckedListener: CompoundButton.OnCheckedChangeListener
        get() = CompoundButton.OnCheckedChangeListener { btn, checked ->
            onCheckedChange(checked, btn)
        }


    override var frequencyState: ReminderFrequencyState = ReminderFrequencyState.Daily()
        private set

    private val _currentWeekDays: MutableSet<DayOfWeekValue> = mutableSetOf()
    override val currentWeekDays: Set<DayOfWeekValue>
        get() = _currentWeekDays


    override fun setDailyFrequency(freq: Int) {
        //todo
        frequencyState = ReminderFrequencyState.Daily(freq)
        currentDailyFrequency = freq
    }

    override fun setDaysOfWeekFrequency(daysOfWeek: Set<DayOfWeekValue>) {
        _currentWeekDays.addAll(daysOfWeek)
        frequencyState = ReminderFrequencyState.WeekDays()
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


    override fun getRealizationDate(begDate: LocalDate) = frequencyState.calculateRealizationDate(
        lastRealizationDate = begDate,
        isBeginning = true
    )


}
