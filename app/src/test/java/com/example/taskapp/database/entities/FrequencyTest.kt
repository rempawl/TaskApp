package com.example.taskapp.database.entities

import com.example.taskapp.utils.reminder.ReminderFrequencyState
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.threeten.bp.DayOfWeek as Day

internal class FrequencyTest {
    @Nested
    @DisplayName("When Frequency is")
    inner class ConvertToFrequencyState {
        @Test
        fun `Frequency(DAILY_FREQUENCY_INDEX,2), Then returns  ReminderFrequencyState Daily(2)`() {
            val expected = ReminderFrequencyState.Daily(2) as ReminderFrequencyState
            val actual = Frequency(ReminderFrequencyState.DAILY_FREQUENCY_INDEX, 2)
                .convertToFrequencyState()
            assertThat(actual, `is`(expected))
        }

        @Nested
        @DisplayName("Frequency(WEEKDAYS_FREQUENCY_INDEX)")
        inner class FrequencyWeekDays {
            @Test
            fun ` 1) Then returns  ReminderFrequencyState Weekdays(Monday)`() {
                val expected =
                    ReminderFrequencyState.WeekDays(setOf(Day.MONDAY.value)) as ReminderFrequencyState
                val actual = Frequency(
                     ReminderFrequencyState.WEEKDAYS_FREQUENCY_INDEX,
                    1
                ).convertToFrequencyState()
                assertThat(actual, `is`(expected))
            }


            @Test
            fun ` 3) Then returns  ReminderFrequencyState Weekdays(Monday,Tuesday)`() {
                val expected =
                    ReminderFrequencyState.WeekDays(
                        setOf(
                            Day.MONDAY.value,
                            Day.TUESDAY.value
                        )
                    ) as ReminderFrequencyState
                val actual = Frequency(
                    ReminderFrequencyState.WEEKDAYS_FREQUENCY_INDEX,
                    3
                ).convertToFrequencyState()
                assertThat(actual, `is`(expected))

            }

            @Test
            fun ` 5) Then returns  ReminderFrequencyState Weekdays(Monday,Wednesday)`() {
                val expected =
                    ReminderFrequencyState.WeekDays(
                        setOf(
                            Day.MONDAY.value,
                            Day.WEDNESDAY.value
                        )
                    ) as ReminderFrequencyState
                val actual = Frequency(
                    ReminderFrequencyState.WEEKDAYS_FREQUENCY_INDEX,
                    5
                ).convertToFrequencyState()
                assertThat(actual, `is`(expected))

            }

            @Test
            fun ` 7) Then returns  ReminderFrequencyState Weekdays(Monday,Tuesday,Wednesday)`() {
                val expected =
                    ReminderFrequencyState
                        .WeekDays(
                            setOf(
                                Day.MONDAY.value,
                                Day.TUESDAY.value,
                                Day.WEDNESDAY.value
                            )
                        ) as ReminderFrequencyState
                val actual = Frequency(
                    ReminderFrequencyState.WEEKDAYS_FREQUENCY_INDEX,
                    7
                ).convertToFrequencyState()
                assertThat(actual, `is`(expected))

            }

        }
    }

}