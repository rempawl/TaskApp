package com.example.taskapp.viewmodels.reminder

import com.example.taskapp.data.reminder.Frequency
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

sealed class ReminderFrequencyState {
    companion object {
        val TODAY: LocalDate =
            LocalDate.now()
        const val INITIAL_FREQUENCY = 1
        const val WEEKDAYS_FREQUENCY_INDEX = 0
        const val DAILY_FREQUENCY_INDEX = 1
    }

    data class Daily(val frequency: Int = INITIAL_FREQUENCY) : ReminderFrequencyState() {

        override fun convertToFrequency() =
            Frequency(
                DAILY_FREQUENCY_INDEX,
                frequency
            )


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

        override fun convertToFrequency() =
            Frequency(WEEKDAYS_FREQUENCY_INDEX,daysOfWeekToInt())

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

            for (thisWeekDay in (startDateDayOfWeekValue + 1)..DayOfWeek.SUNDAY.value) {
                if (daysOfWeek.contains(thisWeekDay)) {
                    return LocalDate.ofEpochDay(startDate.toEpochDay() + (thisWeekDay - startDateDayOfWeekValue))
                }
            }

            for (nextWeekDay in DayOfWeek.MONDAY.value until startDateDayOfWeekValue) {
                if (daysOfWeek.contains(nextWeekDay)) {
                    return LocalDate.ofEpochDay(startDate.toEpochDay() + (7 - startDateDayOfWeekValue + nextWeekDay))
                }
            }

            //next week date
            return LocalDate.ofEpochDay(startDate.toEpochDay() + 7)
        }


        private fun checkIfRealizationDateWasNotUpdatedForLongerThanAWeek(lastRealizationDate: LocalDate) =
            TODAY.toEpochDay() > (lastRealizationDate.toEpochDay() + 7)

        private fun daysOfWeekToInt(): Int {
            var result = 0
            val daysOfWeekValues = DayOfWeek.values()

            //  setting the i-th bit of result to 1 if dayOfWeek value is  inside daysOfWeek set
            for (i in 0..daysOfWeekValues.lastIndex) {
                if (this.daysOfWeek.contains(daysOfWeekValues[i].value)) {
                    result += 1.shl(i)
                }
            }
            return result
        }
    }

//todo refactor
    abstract fun calculateRealizationDate(
        lastRealizationDate: LocalDate,
        isBeginning: Boolean = false
    ): LocalDate

    abstract fun convertToFrequency(): Frequency
}