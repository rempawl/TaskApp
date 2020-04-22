package com.example.taskapp.fragments.addReminder

import com.example.taskapp.database.entities.Frequency
import com.example.taskapp.utils.loadTimeZone
import com.example.taskapp.viewmodels.reminder.ReminderFrequencyState
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.threeten.bp.DayOfWeek.*
import org.threeten.bp.LocalDate

internal class ReminderFrequencyStateTest {
    init {
        loadTimeZone()
    }

    companion object {


        private val monday by lazy { MONDAY.value }
        private val tuesday by lazy { TUESDAY.value }
        private val friday by lazy { FRIDAY.value }
        private val START_DATE: LocalDate by lazy { LocalDate.of(2030, 2, 12) } //tuesday
        private val TODAY: LocalDate by lazy { LocalDate.now() }
    }

    @Nested
    inner class ConvertToFrequency {
        @Nested
        @DisplayName("When ReminderFrequencyState is")
        inner class FreqState {
            @DisplayName("Daily(2)")
            @Test
            fun `Then returns Frequency(DAILY_FREQUENCY_INDEX,2) `() {
                val expected = Frequency(ReminderFrequencyState.DAILY_FREQUENCY_INDEX, 2)
                val actual = ReminderFrequencyState.Daily(2).convertToFrequency()
                assertThat(actual, `is`(expected))
            }

            @DisplayName("Weekdays(Monday)")
            @Test
            fun `Then returns Frequency(WEEKDAYS_FREQUENCY_INDEX,1)`() {
                val expected = Frequency(ReminderFrequencyState.WEEKDAYS_FREQUENCY_INDEX, 1)
                val actual = ReminderFrequencyState.WeekDays(setOf(monday)).convertToFrequency()
                assertThat(actual, `is`(expected))

            }
        }
    }

    @Nested
    inner class CalculateRealizationDate {


        @Nested
        @DisplayName("Given lastRealizationDate equal to ")
        inner class LastRealizationDate {

            @Nested
            @DisplayName("2020-2-11(Tuesday) isBeginning is true, When FrequencyState is")
            inner class IsBeginningTrue {


                @Test
                fun ` Daily(days = 1), Then realizationDate is startDate`() {
                    val expectedDate = START_DATE
                    val freqState = ReminderFrequencyState.Daily(1)
                    val actual = freqState.calculateRealizationDate(START_DATE, true)
                    assertThat(actual, `is`(expectedDate))
                }

                @Test
                fun `WeekDays(Tuesday) realizationDate is startDate`() {
                    val expectedDate = START_DATE
                    val freqState = ReminderFrequencyState.WeekDays(setOf(tuesday))
                    val actual = freqState.calculateRealizationDate(START_DATE, true)
                    assertThat(actual, `is`(expectedDate))
                }

                @Test
                fun `WeekDays(Friday) realizationDate is FridayDate`() {

                    val expectedDate = LocalDate.ofEpochDay(START_DATE.toEpochDay() + 3)
                    val freqState = ReminderFrequencyState.WeekDays(setOf(friday))
                    val actual = freqState.calculateRealizationDate(START_DATE, true)
                    assertThat(actual, `is`(expectedDate))

                }

            }

            @Nested
            @DisplayName("2020-2-11(Tuesday) isBeginning is false, When FrequencyState is")
            inner class IsBeginningFalse {

                @Test
                fun `WeekDays(Monday Tuesday Friday) realizationDate is FridayDate`() {
                    val expectedDate = LocalDate.ofEpochDay(START_DATE.toEpochDay() + 3)
                    val weekdays = ReminderFrequencyState.WeekDays(setOf(monday, tuesday, friday))
                    val actualValue = weekdays.calculateRealizationDate(START_DATE, false)
                    assertThat(actualValue, `is`(expectedDate))
                }

                @Test
                fun `WeekDays(Tuesday)  realizationDate is nextTuesday`() {
                    val expectedDate = LocalDate.ofEpochDay(START_DATE.toEpochDay() + 7)
                    val weekDays = ReminderFrequencyState.WeekDays(setOf(tuesday))
                    val actualValue = weekDays.calculateRealizationDate(START_DATE, false)
                    assertThat(actualValue, `is`(expectedDate))
                }


                @Test
                fun `WeekDays(Monday Tuesday) realizationDate is nextMonday`() {
                    val expectedDate = LocalDate.ofEpochDay(START_DATE.toEpochDay() + 6)
                    val weekDays = ReminderFrequencyState.WeekDays(setOf(monday, tuesday))
                    val actualValue = weekDays.calculateRealizationDate(START_DATE, false)
                    assertThat(actualValue, `is`(expectedDate))
                }
            }
        }
    }
}