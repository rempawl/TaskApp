package com.example.taskapp.fragments.addReminder

import com.example.taskapp.database.entities.Frequency
import com.example.taskapp.loadTimeZone
import com.example.taskapp.utils.reminder.ReminderFrequencyState
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

    private val monday by lazy { MONDAY.value }
    private val tuesday by lazy { TUESDAY.value }
    private val friday by lazy { FRIDAY.value }
    private val startDate: LocalDate by lazy { LocalDate.of(2020, 2, 11) } //tuesday
@Nested
inner class ConvertToFrequency{
    @Nested
    @DisplayName("When ReminderFrequencyState is")
    inner class FreqState {
        @DisplayName("Daily(2)")
        @Test
        fun `Then returns Frequency(DAILY_FREQUENCY_INDEX,2) `(){
            val expected = Frequency(ReminderFrequencyState.DAILY_FREQUENCY_INDEX,2)
            val actual = ReminderFrequencyState.Daily(2).convertToFrequency()
            assertThat(actual,`is`(expected))
        }
        @DisplayName("Weekdays(Monday)")
        @Test
        fun `Then returns Frequency(WEEKDAYS_FREQUENCY_INDEX,1)`(){
            val expected = Frequency(ReminderFrequencyState.WEEKDAYS_FREQUENCY_INDEX,1)
            val actual = ReminderFrequencyState.WeekDays(setOf(monday)).convertToFrequency()
            assertThat(actual,`is`(expected))

        }
    }
}

    @Nested
    inner class CalculateUpdateDate {


        @Nested
        @DisplayName("Given startDate is  2020-2-11(Tuesday)  ")
        inner class StartDate {
             @Nested
             @DisplayName("isBeginning is true, When FrequencyState is")
            inner class IsBeginningTrue {


                 @Test
                 fun `Daily(days = 1), Then UpdateDate is startDate`() {
                     val expectedDate = startDate
                     val freqState = ReminderFrequencyState.Daily(1)
                     val actual = freqState.calculateUpdateDate(startDate, true)
                     assertThat(actual, `is`(expectedDate))
                 }

                 @Test
                 fun ` WeekDays(Tuesday) UpdateDate is startDate`() {
                     val expectedDate = startDate
                     val freqState = ReminderFrequencyState.WeekDays(setOf(tuesday))
                     val actual = freqState.calculateUpdateDate(startDate, true)
                     assertThat(actual, `is`(expectedDate))
                 }

                 @Test
                 fun `WeekDays(Friday) udpateDate is FridayDate`() {

                     val expectedDate = LocalDate.ofEpochDay(startDate.toEpochDay() + 3)
                     val freqState = ReminderFrequencyState.WeekDays(setOf(friday))
                     val actual = freqState.calculateUpdateDate(startDate, true)
                     assertThat(actual, `is`(expectedDate))

                 }

             }

            @Nested
            @DisplayName("isBeginning is false, When FrequencyState is")
            inner class IsBeginningFalse {

                @Test
                fun `WeekDays(Monday Tuesday Friday) updateDate is FridayDate`() {
                    val expectedDate = LocalDate.ofEpochDay(startDate.toEpochDay() + 3)
                    val weekdays = ReminderFrequencyState.WeekDays(setOf(monday, tuesday, friday))
                    val actualValue = weekdays.calculateUpdateDate(startDate,false)
                    assertThat(actualValue, `is`(expectedDate))
                }

                @Test
                fun `WeekDays(Tuesday)  updateDate is nextTuesday`() {
                    val expectedDate = LocalDate.ofEpochDay(startDate.toEpochDay() + 7)
                    val weekDays = ReminderFrequencyState.WeekDays(setOf(tuesday))
                    val actualValue = weekDays.calculateUpdateDate(startDate,false)
                    assertThat(actualValue, `is`(expectedDate))
                }


                @Test
                fun `WeekDays(Monday Tuesday) updateDate is nextMonday`() {
                    val expectedDate = LocalDate.ofEpochDay(startDate.toEpochDay() + 6)
                    val weekDays = ReminderFrequencyState.WeekDays(setOf(monday, tuesday))
                    val actualValue = weekDays.calculateUpdateDate(startDate,false)
                    assertThat(actualValue, `is`(expectedDate))
                }
            }
        }
    }
}