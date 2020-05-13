package com.example.taskapp.database.entities.reminderEntities

import android.os.Parcelable
import com.example.taskapp.viewmodels.reminder.DayOfWeekValue
import com.example.taskapp.viewmodels.reminder.ReminderFrequencyState
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

@Parcelize
data class Frequency(
    val freqState: Int,
    val frequency: Int
) : Parcelable {
    private fun intToDaysOfWeek(): Set<DayOfWeekValue> {
        val days = DayOfWeek.values()
        val result = mutableSetOf<DayOfWeekValue>()
        for (i in 0..days.lastIndex) {
            if (frequency.ushr(i).and(1) == 1) {
                result.add(days[i].value)
            }
        }
        return result
    }

    fun getUpdateDate(lastRealizationDate : LocalDate) =
        this.convertToFrequencyState().calculateRealizationDate(lastRealizationDate)

    fun convertToFrequencyState(): ReminderFrequencyState {
        return when (freqState) {
            ReminderFrequencyState.WEEKDAYS_FREQUENCY_INDEX -> ReminderFrequencyState.WeekDays(
                intToDaysOfWeek()
            )
            ReminderFrequencyState.DAILY_FREQUENCY_INDEX -> ReminderFrequencyState.Daily(
                frequency
            )
            else -> throw IndexOutOfBoundsException()
        }
    }
}