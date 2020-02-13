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

    private val monday by lazy { MONDAY.value }
    private val tuesday by lazy { TUESDAY.value }
    private val friday by lazy { FRIDAY.value }
    private val lastUpdate: LocalDate by lazy { LocalDate.of(2020, 2, 11) } //tuesday

    @Test
    fun calculateUpdateDate_lastUpdateIsBeginningTrueFreqStateDaily_lastUpdateDate() {
        val expectedDate = lastUpdate
        val freqState = ReminderFrequencyState.Daily(1)
        val actual = freqState.calculateUpdateDate(lastUpdate, true)
        assertThat(actual, `is`(expectedDate))
    }

    @Test
    fun calculateUpdateDate_lastUpdateIsBeginningTrueFreqStateWeekDaysTuesday_lastUpdateDate() {
        val expectedDate = lastUpdate
        val freqState = ReminderFrequencyState.WeekDays(setOf(tuesday))
        val actual = freqState.calculateUpdateDate(lastUpdate, true)
        assertThat(actual, `is`(expectedDate))
    }

    @Test
    fun calculateUpdateDate_lastUpdateIsBeginningTrueFreqStateWeekDaysFriday_FridayDate() {

        val expectedDate = LocalDate.ofEpochDay(lastUpdate.toEpochDay() +3)
        val freqState = ReminderFrequencyState.WeekDays(setOf(friday))
        val actual = freqState.calculateUpdateDate(lastUpdate, true)
        assertThat(actual, `is`(expectedDate))

    }


    @Test
    fun calculateUpdateDate_lastUpdateWeekDaysMondayTuesdayFridayIsBeginningFalse_FridayDate() {
        val expectedDate = LocalDate.ofEpochDay(lastUpdate.toEpochDay() + 3)
        val weekdays = ReminderFrequencyState.WeekDays(setOf(monday, tuesday, friday))
        val actualValue = weekdays.calculateUpdateDate(lastUpdate)
        assertThat(actualValue, `is`(expectedDate))
    }

    @Test
    fun calculateUpdateDate_lastUpdateWeekDaysTuesdayIsBeginningFalse_nextTuesday() {
        val expectedDate = LocalDate.ofEpochDay(lastUpdate.toEpochDay() + 7)
        val weekDays = ReminderFrequencyState.WeekDays(setOf(tuesday))
        val actualValue = weekDays.calculateUpdateDate(lastUpdate)
        assertThat(actualValue, `is`(expectedDate))
    }


    @Test
    fun calculateUpdateDate_lastUpdateWeekDaysMondayTuesdayIsBeginningFalse_nextMonday() {
        val expectedDate = LocalDate.ofEpochDay(lastUpdate.toEpochDay() + 6)
        val weekDays = ReminderFrequencyState.WeekDays(setOf(monday, tuesday))
        val actualValue = weekDays.calculateUpdateDate(lastUpdate)
        assertThat(actualValue, `is`(expectedDate))
    }

}