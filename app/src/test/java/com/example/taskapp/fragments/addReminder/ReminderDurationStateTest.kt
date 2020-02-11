package com.example.taskapp.fragments.addReminder

import com.example.taskapp.loadTimeZone
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
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

    @Test
    fun calculateEndDate_daysDurationTenDaysBegDate_TenDaysFromBegDate() {
        val duration = ReminderDurationState.DaysDuration(10)
        assertThat(
            duration.calculateEndDate(begDate),
            `is`(LocalDate.ofYearDay(begDate.year, begDate.dayOfYear + 10))
        )
    }

    @Test
    fun calculateEndDate_daysDuration366DaysBegDate_YearFromBegDate() {
        val duration = ReminderDurationState.DaysDuration(366)
        assertThat(
            duration.calculateEndDate(begDate),
            `is`(LocalDate.of(begDate.year + 1, begDate.month, begDate.dayOfMonth))
        )
    }

    @Test
    fun calculateEndDate_noEndDate_null() {
        assertThat(noEndDate.calculateEndDate(begDate), `is`(null as LocalDate?))
    }


    @Test
    fun calculateEndDate_EndDate21_02_2022_21_02_2022() {
        val endDate = ReminderDurationState.EndDate(LocalDate.of(2022, 2, 21))
        assertThat(endDate.calculateEndDate(begDate), `is`(endDate.date))
    }
//    @Test
//    fun calculateEndDate_daysDuration() {
//    }
//    @Test
//    fun calculateEndDate_daysDuration() {
//    }
}