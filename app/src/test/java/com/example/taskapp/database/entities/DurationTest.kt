package com.example.taskapp.database.entities

import com.example.taskapp.data.reminder.Duration
import com.example.taskapp.viewmodels.reminder.ReminderDurationState
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
        fun `Duration(NoEndDateIndex,0), Then returns ReminderFrequencyState NoEndDate`(){
            val actual : ReminderDurationState = Duration(
                ReminderDurationState.NO_END_DATE_DURATION_INDEX, 0
            )
                .convertToDurationState()
            val expected : ReminderDurationState = ReminderDurationState.NoEndDate
            assertThat(actual,`is`(expected))
        }
        @Test
        fun `Duration(EndDateIndex,20-07-2014 toEpoch) ,Then returns ReminderFrequencyState EndDate`(){
            val date = LocalDate.of(2014,7,20)
            val actual : ReminderDurationState = Duration(
                ReminderDurationState.END_DATE_DURATION_INDEX, date.toEpochDay()
            )
                .convertToDurationState()
            val expected : ReminderDurationState = ReminderDurationState.EndDate(date)
            assertThat(actual,`is`(expected))
        }
        @Test
        fun `Duration(DaysDurationIndex,2), Then returns ReminderFrequencyState Daily92)`(){
            val actual : ReminderDurationState = Duration(
                ReminderDurationState.DAYS_DURATION_INDEX, 2
            )
                .convertToDurationState()
            val expected : ReminderDurationState = ReminderDurationState.DaysDuration(2)
            assertThat(actual,`is`(expected))
        }

    }
}