package com.example.taskapp.fragments.addReminder

import com.example.taskapp.loadTimeZone
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.threeten.bp.DayOfWeek.*
import org.threeten.bp.LocalDate

internal class ReminderFrequencyStateTest {
    init {
        loadTimeZone()
    }

    private val monday = MONDAY.value
    private val tuesday = TUESDAY.value
    private val friday = FRIDAY.value
    val lastUpdate = LocalDate.of(2020,2,11) //tuesday


    @Test
    fun getUpdateDate_lastUpdateTuesdayWeekDaysMondayTuesdayFriday_FridayDate() {
        val expectedDate = LocalDate.ofEpochDay(lastUpdate.toEpochDay()+3)
        val weekdays = ReminderFrequencyState.WeekDays(setOf(monday,tuesday,friday))
        val actualValue = weekdays.getUpdateDate(lastUpdate)
        assertThat(actualValue,`is`(expectedDate))
    }

    @Test
    fun getUpdateDate_lastUpdateTuesdayWeekDaysTuesday_nextTuesday(){
        val expectedDate = LocalDate.ofEpochDay(lastUpdate.toEpochDay()+7)
        val weekDays = ReminderFrequencyState.WeekDays(setOf(tuesday))
        val actualValue = weekDays.getUpdateDate(lastUpdate)
        assertThat(actualValue,`is`(expectedDate))
    }

    @Test
    fun getUpdateDate_lastUpdateTuesdayWeekDaysMondayTuesday_nextMonday(){
        val expectedDate = LocalDate.ofEpochDay(lastUpdate.toEpochDay()+6)
        val weekDays = ReminderFrequencyState.WeekDays(setOf(monday,tuesday))
        val actualValue = weekDays.getUpdateDate(lastUpdate)
        assertThat(actualValue,`is`(expectedDate))
    }

}