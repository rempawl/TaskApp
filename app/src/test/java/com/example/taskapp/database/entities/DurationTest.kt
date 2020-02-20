package com.example.taskapp.database.entities

import com.example.taskapp.utils.reminder.ReminderDurationState
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.threeten.bp.LocalDate

internal class DurationTest{
    @Nested
    @DisplayName("When Duration is")
    inner class ConvertToDurationState{
        @Test
        @DisplayName("Duration(NoEndDateIndex,0) ")
        fun `Then returns ReminderFrequencyState NoEndDate`(){
            val actual : ReminderDurationState = Duration(ReminderDurationState.NO_END_DATE_DURATION_INDEX,0)
                .convertToDurationState()
            val expected : ReminderDurationState = ReminderDurationState.NoEndDate
            assertThat(actual,`is`(expected))
        }
        @Test
        @DisplayName("Duration(EndDateIndex,20-07-2014 toEpoch) ")
        fun `Then returns ReminderFrequencyState EndDate`(){
            val date = LocalDate.of(2014,7,20)
            val actual : ReminderDurationState = Duration(ReminderDurationState.END_DATE_DURATION_INDEX,date.toEpochDay())
                .convertToDurationState()
            val expected : ReminderDurationState = ReminderDurationState.EndDate(date)
            assertThat(actual,`is`(expected))
        }
        @Test
        @DisplayName("Duration(DaysDurationIndex,2) ")
        fun `Then returns ReminderFrequencyState Daily92)`(){
            val actual : ReminderDurationState = Duration(ReminderDurationState.DAYS_DURATION_INDEX,2)
                .convertToDurationState()
            val expected : ReminderDurationState = ReminderDurationState.DaysDuration(2)
            assertThat(actual,`is`(expected))
        }

    }
}