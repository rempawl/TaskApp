package com.example.taskapp.viewmodels.reminder

import com.example.taskapp.data.reminder.Frequency
import com.example.taskapp.utils.DateUtils.TODAY
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

sealed class ReminderFrequencyState {
    companion object {
        const val INITIAL_FREQUENCY = 1
        const val WEEKDAYS_FREQUENCY_INDEX = 0
        const val DAILY_FREQUENCY_INDEX = 1
    }

    abstract fun calculateRealizationDate(
        lastRealizationDate: LocalDate,
        isBeginning: Boolean = false
    ): LocalDate

    abstract fun convertToFrequency(): Frequency

    data class Daily(val frequency: Int = INITIAL_FREQUENCY) : ReminderFrequencyState() {

        override fun convertToFrequency() =Frequency(                DAILY_FREQUENCY_INDEX,
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
            Frequency(WEEKDAYS_FREQUENCY_INDEX, daysOfWeekToInt())

        override fun calculateRealizationDate(
            lastRealizationDate: LocalDate,
            isBeginning: Boolean
        ): LocalDate {
            val startDate = setStartDate(lastRealizationDate)
            val startDateDayOfWeekValue = startDate.dayOfWeek.value

            if (isBeginning && daysOfWeek.contains(startDateDayOfWeekValue)) {
                return startDate
            }

            for (thisWeekDay in (startDateDayOfWeekValue + 1)..DayOfWeek.SUNDAY.value) {
                if (daysOfWeek.contains(thisWeekDay)) {
                    return LocalDate.ofEpochDay(
                        getThisWeekDayValue(startDate, thisWeekDay, startDateDayOfWeekValue)
                    )
                }
            }

            for (nextWeekDay in DayOfWeek.MONDAY.value until startDateDayOfWeekValue) {
                if (daysOfWeek.contains(nextWeekDay)) {
                    return LocalDate.ofEpochDay(
                        getNextWeekDayValue(startDate, startDateDayOfWeekValue, nextWeekDay)
                    )
                }
            }
            return LocalDate.ofEpochDay(startDate.toEpochDay() + 7)
        }

        private fun getNextWeekDayValue(
            startDate: LocalDate,
            startDateDayOfWeekValue: Int,
            nextWeekDay: Int
        ) = startDate.toEpochDay() + (7 - startDateDayOfWeekValue + nextWeekDay)

        private fun getThisWeekDayValue(
            startDate: LocalDate,
            thisWeekDay: Int,
            startDateDayOfWeekValue: Int
        ) = startDate.toEpochDay() + (thisWeekDay - startDateDayOfWeekValue)

        private fun setStartDate(lastRealizationDate: LocalDate) =
            if (checkIfRealizationDateWasNotUpdatedForLongerThanAWeek(
                    lastRealizationDate
                )
            ) {
                TODAY
            } else {
                lastRealizationDate
            }


        private fun checkIfRealizationDateWasNotUpdatedForLongerThanAWeek(lastRealizationDate: LocalDate) =
            TODAY.toEpochDay() > (lastRealizationDate.toEpochDay() + 7)

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


}