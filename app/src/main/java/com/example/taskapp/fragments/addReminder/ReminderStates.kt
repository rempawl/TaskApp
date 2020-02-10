package com.example.taskapp.fragments.addReminder

import com.example.taskapp.database.entities.Duration
import com.example.taskapp.database.entities.Frequency
import com.example.taskapp.utils.Converters
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate


typealias DayOfWeekHash = Int

sealed class ReminderFrequencyState {
    companion object {
        const val INITIAL_FREQUENCY = 1
        const val WEEKDAYS_FREQUENCY_INDEX = 0
        const val DAILY_FREQUENCY_INDEX = 1
    }

    data class Daily(val frequency: Int = INITIAL_FREQUENCY) : ReminderFrequencyState()

    data class WeekDays(val daysOfWeek: Set<DayOfWeekHash> = emptySet()) :
        ReminderFrequencyState() {
        /**
         *  *  setting the i-th bit of result to 1 if dayOfWeek HashCode is  inside [daysOfWeek] set
         */
        fun daysOfWeekToInt(): Int {
            var result = 0
            val days = DayOfWeek.values()
            for (i in 0..days.lastIndex) {
                if (this.daysOfWeek.contains(days[i].hashCode())) {
                    result += 1.shl(i)
                }
            }
            return result
        }

    }

    fun convertToFrequency(): Frequency {
        return when (this) {
            is WeekDays -> Frequency(WEEKDAYS_FREQUENCY_INDEX,daysOfWeekToInt())
            is Daily -> Frequency(DAILY_FREQUENCY_INDEX,frequency)
        }

    }
}

sealed class ReminderDurationState {

    object NoEndDate : ReminderDurationState()

    data class EndDate(val date: LocalDate = LocalDate.now()) : ReminderDurationState()

    data class DaysDuration(val days: Int = 0) : ReminderDurationState()

    fun convertToDuration(): Duration {
        return when (this) {
            is NoEndDate -> Duration(NO_END_DATE_DURATION_INDEX)
            is EndDate -> Duration(END_DATE_DURATION_INDEX,
                Converters.getInstance().localDateToLong(date))
            is DaysDuration -> Duration(DAYS_DURATION_INDEX,duration = days.toLong())
        }
    }

    companion object {
        const val END_DATE_DURATION_INDEX = 1
        const val NO_END_DATE_DURATION_INDEX = 0
        const val DAYS_DURATION_INDEX = 2
    }
}



