package com.example.taskapp.viewmodels.reminder

import com.example.taskapp.database.entities.reminderEntities.Duration
import org.threeten.bp.LocalDate

typealias DayOfWeekValue = Int

sealed class ReminderDurationState {

    object NoEndDate : ReminderDurationState() {
        override fun convertToDuration(): Duration =Duration(NO_END_DATE_DURATION_INDEX)
        override fun calculateEndDate(begDate: LocalDate): LocalDate =
            LocalDate.ofEpochDay(begDate.toEpochDay() - 1)
    }

    data class EndDate(val date: LocalDate = LocalDate.now()) : ReminderDurationState() {
        override fun convertToDuration(): Duration =
            Duration(
                END_DATE_DURATION_INDEX,
                date.toEpochDay()
            )

        override fun calculateEndDate(begDate: LocalDate): LocalDate = date


    }

    data class DaysDuration(val days: Int = 0) : ReminderDurationState() {
        override fun
                convertToDuration(): Duration =
            Duration(
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