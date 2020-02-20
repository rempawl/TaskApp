package com.example.taskapp.utils.reminder

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
        /**
         * fun is returning beginningDate as updateDate if [startDate] is beginning date
         */
        override fun calculateUpdateDate(startDate: LocalDate, isBeginning: Boolean): LocalDate =
            LocalDate.ofEpochDay(startDate.toEpochDay() + if (!isBeginning) frequency else 0)
    }


    data class WeekDays(val daysOfWeek: Set<DayOfWeekValue> = emptySet()) :
        ReminderFrequencyState() {

        override fun convertToFrequency() = Frequency(WEEKDAYS_FREQUENCY_INDEX, daysOfWeekToInt())

        /**
         * checking if [daysOfWeek] contains days after or before [startDate] [DayOfWeek]
         * if it doesn't returning next week date
         */
        override fun calculateUpdateDate(startDate: LocalDate, isBeginning: Boolean): LocalDate {
            val begIndex = startDate.dayOfWeek.value


            if (isBeginning && startDate.dayOfWeek.value == begIndex &&
                daysOfWeek.contains(begIndex)
            ) {
                return startDate
            }

            for (thisWeek in begIndex + 1..DayOfWeek.SUNDAY.value) {
                if (daysOfWeek.contains(thisWeek)) {
                    return LocalDate.ofEpochDay(startDate.toEpochDay() + (thisWeek - begIndex))
                }
            }
            for (nextWeek in DayOfWeek.MONDAY.value until begIndex) {
                if (daysOfWeek.contains(nextWeek)) {
                    println(nextWeek)
                    return LocalDate.ofEpochDay(startDate.toEpochDay() + (7 - begIndex + nextWeek))
                }
            }
            return LocalDate.ofEpochDay(startDate.toEpochDay() + 7)
        }

        /**
         *  *  setting the i-th bit of result to 1 if dayOfWeek HashCode is  inside [daysOfWeek] set
         */
        private fun daysOfWeekToInt(): Int {
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

    abstract fun calculateUpdateDate(startDate: LocalDate, isBeginning: Boolean = false): LocalDate
    abstract fun convertToFrequency(): Frequency
}

sealed class ReminderDurationState {

    object NoEndDate : ReminderDurationState() {
        override fun convertToDuration(): Duration = Duration(NO_END_DATE_DURATION_INDEX)
        override fun calculateEndDate(begDate: LocalDate): LocalDate =
            LocalDate.ofEpochDay(begDate.toEpochDay() - 1)

    }

    data class EndDate(val date: LocalDate = LocalDate.now()) : ReminderDurationState() {
        override fun convertToDuration(): Duration =
            Duration(END_DATE_DURATION_INDEX, Converters.getInstance().localDateToLong(date))

        override fun calculateEndDate(begDate: LocalDate): LocalDate = date


    }

    data class DaysDuration(val days: Int = 0) : ReminderDurationState() {
        override fun convertToDuration(): Duration = Duration(
            DAYS_DURATION_INDEX, duration = days.toLong()
        )

        override fun calculateEndDate(begDate: LocalDate): LocalDate =
            LocalDate.ofEpochDay(begDate.toEpochDay() + days)


    }

    abstract fun convertToDuration(): Duration

    abstract fun calculateEndDate(begDate: LocalDate): LocalDate


    companion object {
        const val END_DATE_DURATION_INDEX = 1
        const val NO_END_DATE_DURATION_INDEX = 0
        const val DAYS_DURATION_INDEX = 2
    }
}



