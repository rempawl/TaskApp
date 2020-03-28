package com.example.taskapp.utils.reminder

import com.example.taskapp.database.entities.Duration
import com.example.taskapp.database.entities.Frequency
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate


typealias DayOfWeekValue = Int

sealed class ReminderFrequencyState {
    companion object {
        val TODAY: LocalDate = LocalDate.now()
        const val INITIAL_FREQUENCY = 1
        const val WEEKDAYS_FREQUENCY_INDEX = 0
        const val DAILY_FREQUENCY_INDEX = 1
    }

    data class Daily(val frequency: Int = INITIAL_FREQUENCY) : ReminderFrequencyState() {

        override fun convertToFrequency() = Frequency(DAILY_FREQUENCY_INDEX, frequency)


        /**
         * fun returns beginningDate as updateDate if [lastRealizationDate] is beginning date
         */
        override fun calculateRealizationDate(
            lastRealizationDate: LocalDate,
            isBeginning: Boolean
        ): LocalDate {
            var date = lastRealizationDate
            val interval = if (!isBeginning) frequency else 0

            do {
                date =
                    LocalDate.ofEpochDay(date.toEpochDay() + interval)
            } while (date.isBefore(TODAY))
            return date

        }

    }


    data class WeekDays(val daysOfWeek: Set<DayOfWeekValue> = emptySet()) :
        ReminderFrequencyState() {

        override fun convertToFrequency() = Frequency(WEEKDAYS_FREQUENCY_INDEX, daysOfWeekToInt())

        override fun calculateRealizationDate(
            lastRealizationDate: LocalDate,
            isBeginning: Boolean
        ): LocalDate {
            val startDate = if (checkIfRealizationDateWasNotUpdatedForLongerThanAWeek(
                    lastRealizationDate
                )
            ) {
                TODAY
            } else {
                lastRealizationDate
            }
            val startDateDayOfWeekValue = startDate.dayOfWeek.value

            if (isBeginning && daysOfWeek.contains(startDateDayOfWeekValue)) {
                return startDate
            }

            //check if  dayOfWeek contains dates that are after startDateDayOfWeek and before or equal sunday
            for (thisWeekDay in (startDateDayOfWeekValue + 1)..DayOfWeek.SUNDAY.value) {
                if (daysOfWeek.contains(thisWeekDay)) {
                    return LocalDate.ofEpochDay(startDate.toEpochDay() + (thisWeekDay - startDateDayOfWeekValue))
                }
            }

            //check if daysOfWeek contains dates that are after  or equal to monday and before startDateDayOfWeek
            for (nextWeekDay in DayOfWeek.MONDAY.value until startDateDayOfWeekValue) {
                if (daysOfWeek.contains(nextWeekDay)) {
                    println(nextWeekDay)
                    return LocalDate.ofEpochDay(startDate.toEpochDay() + (7 - startDateDayOfWeekValue + nextWeekDay))
                }
            }

            //next week date
            return LocalDate.ofEpochDay(startDate.toEpochDay() + 7)
        }


        private fun checkIfRealizationDateWasNotUpdatedForLongerThanAWeek(lastRealizationDate: LocalDate) =
            TODAY.toEpochDay() > (lastRealizationDate.toEpochDay() + 7)

        //  setting the i-th bit of result to 1 if dayOfWeek HashCode is  inside [daysOfWeek] set
        private fun daysOfWeekToInt(): Int {
            var result = 0
            val daysOfWeekValues = DayOfWeek.values()

            for (i in 0..daysOfWeekValues.lastIndex) {
                if (this.daysOfWeek.contains(daysOfWeekValues[i].value)) {
                    result += 1.shl(i)
                }
            }
            return result
        }
    }


    abstract fun calculateRealizationDate(
        lastRealizationDate: LocalDate,
        isBeginning: Boolean = false
    ): LocalDate

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
            Duration(END_DATE_DURATION_INDEX, date.toEpochDay())

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



