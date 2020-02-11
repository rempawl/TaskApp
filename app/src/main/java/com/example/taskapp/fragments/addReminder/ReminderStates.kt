package com.example.taskapp.fragments.addReminder

import com.example.taskapp.database.entities.Duration
import com.example.taskapp.database.entities.Frequency
import com.example.taskapp.utils.Converters
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate


typealias DayOfWeekValue = Int

sealed class ReminderFrequencyState {
    companion object {
        const val INITIAL_FREQUENCY = 1
        const val WEEKDAYS_FREQUENCY_INDEX = 0
        const val DAILY_FREQUENCY_INDEX = 1
    }

    data class Daily(val frequency: Int = INITIAL_FREQUENCY) : ReminderFrequencyState() {
        override fun convertToFrequency() = Frequency(DAILY_FREQUENCY_INDEX, frequency)

        override fun getUpdateDate(lastUpdate: LocalDate): LocalDate =
            LocalDate.ofEpochDay(lastUpdate.toEpochDay() + frequency)
    }


    data class WeekDays(val daysOfWeek: Set<DayOfWeekValue> = emptySet()) :
        ReminderFrequencyState() {

        override fun convertToFrequency() = Frequency(WEEKDAYS_FREQUENCY_INDEX, daysOfWeekToInt())


        /**
         * checking if [daysOfWeek] contains days after or before [lastUpdate] [DayOfWeek]
         * if it doesn't returning next week date
         */
        override fun getUpdateDate(lastUpdate: LocalDate): LocalDate {
            val days = DayOfWeek.values()
            val begIndex = lastUpdate.dayOfWeek.value
//            Log.d(MainActivity.TAG,begIndex.toString())
            println(begIndex)

            for (thisWeek in begIndex+1..DayOfWeek.SUNDAY.value) {
                if (daysOfWeek.contains(thisWeek)) {
                    return LocalDate.ofEpochDay(lastUpdate.toEpochDay() + (thisWeek - begIndex))
                }
            }
            for (nextWeek in DayOfWeek.MONDAY.value until begIndex) {
                if (daysOfWeek.contains(nextWeek)) {
                    println(nextWeek)
                    return LocalDate.ofEpochDay(lastUpdate.toEpochDay() + (7 - begIndex + nextWeek))
                }
            }
            return LocalDate.ofEpochDay(lastUpdate.toEpochDay() + 7)
        }
        /**
         *  *  setting the i-th bit of result to 1 if dayOfWeek HashCode is  inside [daysOfWeek] set
         */
        fun daysOfWeekToInt(): Int {
            var result = 0
            val days = DayOfWeek.values()
            for (i in 0..days.lastIndex) {
                if (this.daysOfWeek.contains(days[i].value)) {
                    result += 1.shl(i)
                }
            }
            return result
        }
    }

    abstract fun getUpdateDate(lastUpdate: LocalDate): LocalDate
    abstract fun convertToFrequency(): Frequency
}

sealed class ReminderDurationState {

    object NoEndDate : ReminderDurationState()

    data class EndDate(val date: LocalDate = LocalDate.now()) : ReminderDurationState()

    data class DaysDuration(val days: Int = 0) : ReminderDurationState()

    fun convertToDuration(): Duration {
        return when (this) {
            is NoEndDate -> Duration(NO_END_DATE_DURATION_INDEX)
            is EndDate -> Duration(
                END_DATE_DURATION_INDEX,
                Converters.getInstance().localDateToLong(date)
            )
            is DaysDuration -> Duration(DAYS_DURATION_INDEX, duration = days.toLong())
        }
    }

    fun calculateEndDate(begDate: LocalDate): LocalDate? {
        return when (this) {
            is NoEndDate -> null
            is EndDate -> date
            is DaysDuration -> LocalDate.ofEpochDay(begDate.toEpochDay() + days)
        }
    }

    companion object {
        const val END_DATE_DURATION_INDEX = 1
        const val NO_END_DATE_DURATION_INDEX = 0
        const val DAYS_DURATION_INDEX = 2
    }
}



