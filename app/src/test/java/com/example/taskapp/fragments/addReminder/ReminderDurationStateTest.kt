package com.example.taskapp.fragments.addReminder

import com.example.taskapp.database.entities.Duration
import com.example.taskapp.utils.loadTimeZone
import com.example.taskapp.viewmodels.reminder.ReminderDurationState
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.threeten.bp.LocalDate

internal class ReminderDurationStateTest {

    init {
        loadTimeZone()
    }

    private val begDate = LocalDate.of(2020, 1, 11) as LocalDate
    private val noEndDate = ReminderDurationState.NoEndDate
    private val endDate = LocalDate.of(2022, 2, 21)


    @Nested
    inner class ConvertToDuration {
        @Nested
        @DisplayName("When ReminderDurationState is")
        inner class ReminderDurState {
            @Test
            fun `EndDate(date = 21-02-2022),then Duration equals  Duration(END_DATE_DURATION_INDEX,endDate toEpoch)  `() {
                val durState = ReminderDurationState.EndDate(endDate)
                val expected =
                    Duration(ReminderDurationState.END_DATE_DURATION_INDEX, endDate.toEpochDay())
                val actual = durState.convertToDuration()
                assertThat(actual, `is`(expected))
            }

            @Test
            fun `DaysDuration(10 days), then Duration equals Duration(DAYS_DURATION_INDEX,10)`() {
                val durationState = ReminderDurationState.DaysDuration(10)
                val expected = Duration(ReminderDurationState.DAYS_DURATION_INDEX, 10)
                val actual = durationState.convertToDuration()
                assertThat(actual, `is`(expected))
            }
        }
    }

    @Nested
    inner class CalculateEndDate {
        @Nested
        @DisplayName("Given begDate 2020-01-11")
        inner class BeginningDate {
            @Nested
            @DisplayName("When ReminderDuratonState is")
            inner class ReminderDurState {


                @Test
                fun `DaysDuration  10 days, Then expiration date is ten days after BegDate`() {
                    val expectedDate = LocalDate.ofEpochDay(begDate.toEpochDay() + 10)
                    val duration = ReminderDurationState.DaysDuration(10)
                    assertThat(
                        duration.calculateEndDate(begDate),
                        `is`(expectedDate)
                    )
                }

                @Test
                @DisplayName(" DaysDuration(366 days)")
                fun `Then expirationDate is  year after begDate`() {
                    val duration = ReminderDurationState.DaysDuration(366)
                    val actual = duration.calculateEndDate(begDate)
                    val expected = LocalDate.of(begDate.year + 1, begDate.month, begDate.dayOfMonth)
                    assertThat(actual, `is`(expected))
                }

                @Test
                fun `NoEndDate, Then expiration date is begDate - 1`() {
                    val actual = noEndDate.calculateEndDate(begDate)
                    val expectedDate = LocalDate.ofEpochDay(begDate.toEpochDay() - 1)
                    assertThat(actual, `is`(expectedDate))
                }


                @Test
                fun `EndDate 21-02-2022, Then expiration date is  21-02-2022 `() {
                    val endDate = ReminderDurationState.EndDate(endDate)
                    val actual = endDate.calculateEndDate(begDate)
                    assertThat(actual, `is`(endDate.date))
                }
            }
        }
    }
}