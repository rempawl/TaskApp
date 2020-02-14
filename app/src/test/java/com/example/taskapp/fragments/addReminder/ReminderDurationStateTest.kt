package com.example.taskapp.fragments.addReminder

import com.example.taskapp.loadTimeZone
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

import org.threeten.bp.LocalDate

internal class ReminderDurationStateTest {

    init {
        loadTimeZone()
    }

    private val begDate = LocalDate.of(2020, 1, 11) as LocalDate
    private val noEndDate = ReminderDurationState.NoEndDate

    @Test
    fun convertToDuration() {
    }

    @Nested
    inner class CalculateEndDate {
        @Test
        fun ` daysDuration TenDays, beginningDate BegDate, Ten Days After BegDate`() {
            val expectedDate = LocalDate.ofEpochDay(begDate.toEpochDay() + 10)
            val duration = ReminderDurationState.DaysDuration(10)
            assertThat(
                duration.calculateEndDate(begDate),
                `is`(expectedDate)
            )
        }

        @Test
        fun `daysDuration 366 Days, beginning begDate, Year after begDate`() {
            val duration = ReminderDurationState.DaysDuration(366)
            assertThat(
                duration.calculateEndDate(begDate),
                `is`(LocalDate.of(begDate.year + 1, begDate.month, begDate.dayOfMonth))
            )
        }

        @Test
        fun `noEndDate, begDate - 1`() {
            val expectedDate =LocalDate.ofEpochDay(begDate.toEpochDay()-1)
            assertThat(noEndDate.calculateEndDate(begDate), `is`(expectedDate))
        }


        @Test
        fun ` endDate 21-02-2022,  21-02-2022 `() {
            val endDate = ReminderDurationState.EndDate(LocalDate.of(2022, 2, 21))
            assertThat(endDate.calculateEndDate(begDate), `is`(endDate.date))
        }
    }
//    @Test
//    fun calculateEndDate_daysDuration() {
//    }
//    @Test
//    fun calculateEndDate_daysDuration() {
//    }
}