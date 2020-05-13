package com.example.taskapp.viewmodels.reminder.frequencyModel

import android.widget.CompoundButton
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.taskapp.BR
import com.example.taskapp.database.entities.reminderEntities.Frequency
import com.example.taskapp.viewmodels.reminder.DayOfWeekValue
import com.example.taskapp.viewmodels.reminder.ReminderFrequencyState
import org.threeten.bp.LocalDate


abstract class FrequencyModel : BaseObservable() {

    //init with tuesday
    protected val checkedDays = mutableSetOf<DayOfWeekValue>(2)

    val onCheckedListener = CompoundButton.OnCheckedChangeListener { btn, checked ->
        onCheckedChange(checked, btn)
    }

    abstract val frequencyState: ReminderFrequencyState

    @Bindable
    var currentDailyFrequency = ReminderFrequencyState.INITIAL_FREQUENCY
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentDailyFrequency)
        }


    abstract val currentWeekDays: Set<DayOfWeekValue>

    abstract fun setDailyFrequency(freq: Int = currentDailyFrequency)

    abstract fun setDaysOfWeekFrequency(daysOfWeek: Set<DayOfWeekValue>?)

    fun getFrequency(): Frequency = frequencyState.convertToFrequency()

    fun getRealizationDate(begDate: LocalDate) = frequencyState.calculateRealizationDate(
        lastRealizationDate = begDate,
        isBeginning = true
    )

    private fun onCheckedChange(isChecked: Boolean, box: CompoundButton) {
        if (isChecked) {
            checkedDays.add(box.id)
        } else {
            if (checkedDays.size == 1) {
                box.isChecked = true
            } else {
                checkedDays.remove(box.id)
            }
        }
    }


}