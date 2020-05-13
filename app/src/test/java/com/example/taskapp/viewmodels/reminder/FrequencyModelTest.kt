package com.example.taskapp.viewmodels.reminder

import com.example.taskapp.database.entities.reminder.Frequency
import com.example.taskapp.viewmodels.reminder.frequencyModel.EditTaskFrequencyModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.threeten.bp.DayOfWeek as Day



internal class FrequencyModelTest {
    private lateinit var model: FrequencyModel

    @Nested
    inner class InitWithNull {
        @BeforeEach
        fun setUp() {
            model =
                EditTaskFrequencyModel(
                    null
                )
        }

        @Test
        fun `When Frequency = null, then initial freqState is Daily and currentDailyFreq is INITIAL_FREQ`() {
            val actualState = model.frequencyState
            val expectedState = ReminderFrequencyState.Daily()
            val expectedFreq = expectedState.frequency
            val actualFreq = model.currentDailyFrequency
            assertEquals(expectedFreq, actualFreq)
            assertEquals(expectedState, actualState)

        }

        @Nested
        inner class SetDaysOfWeekFrequency {
            @Test
            fun `Given Monday Friday, Then freqState is DaysOfWeek and currentWeekDays are monday friday`() {
                val expectedDays = setOf<DayOfWeekValue>(Day.MONDAY.value, Day.FRIDAY.value)
                model.setDaysOfWeekFrequency(expectedDays)
                val actualState = model.frequencyState as ReminderFrequencyState.WeekDays
                val actualDays = model.currentWeekDays
                assertEquals((ReminderFrequencyState.WeekDays(expectedDays)), actualState)
                assertEquals(expectedDays, actualDays)
            }

        }

        @Nested
        inner class SetDailyFrequency {
            @Test
            fun `Given freq = 10, then freqState is Daily and currentDailyFrequency is 10`() {
                val expectedFreq = 10
                model.setDailyFrequency(expectedFreq)
                val actualState = model.frequencyState
                val actualFreq = model.currentDailyFrequency
                val expectedState = ReminderFrequencyState.Daily(expectedFreq)
                assertEquals(expectedState, actualState)
                assertEquals(expectedFreq, actualFreq)
            }
        }

    }

    @Nested
    @DisplayName("Init With Frequency(DailyIndex,2)")
    inner class InitWithFrequencyDaily {
        private val freq =
            Frequency(
                ReminderFrequencyState.DAILY_FREQUENCY_INDEX,
                2
            )

        @BeforeEach
        fun setUp() {
            model =
                EditTaskFrequencyModel(
                    freq
                )
        }

        @Test
        fun `then freqState is Daily and currentDailyFreq is 2`() {
            val actualFreq = model.currentDailyFrequency
            val actualState = model.frequencyState
            val expectedFreq = freq.frequency
            val expectedState = ReminderFrequencyState.Daily(expectedFreq)
            assertEquals(expectedFreq, actualFreq)
            assertEquals(expectedState, actualState)
        }

        @Nested
        inner class SetWeekDaysFrequency {
            @Test
            fun `Given Monday Friday,then freqState is DaysOfWeek and currentDays are monday friday`() {
                val expectedDays = setOf<DayOfWeekValue>(Day.FRIDAY.value, Day.MONDAY.value)
                val expectedState = ReminderFrequencyState.WeekDays(expectedDays)
                model.setDaysOfWeekFrequency(expectedDays)
                val actualState = model.frequencyState
                val actualDays = model.currentWeekDays
                assertEquals(expectedState, actualState)
                assertEquals(expectedDays, actualDays)
            }

        }
        @Nested
        inner class SetDailyFrequency       {
            @Test
            fun `Given 3, then freqState is Daily and currentDailyFreq is 3 `(){
                val expectedFreq = 3
                val expectedState = ReminderFrequencyState.Daily(expectedFreq)
                model.setDailyFrequency(expectedFreq)
                val actualState = model.frequencyState
                val actualFreq = model.currentDailyFrequency
                assertEquals(expectedFreq,actualFreq)
                assertEquals(expectedState,actualState)
            }

        }
    }

    @Nested
    @DisplayName("Init with Weekdays, Given initial Frequency = freq,")
    inner class InitWithWeekDaysFrequency {
        private val initDays = setOf(Day.MONDAY.value, Day.TUESDAY.value)
        private val freq = ReminderFrequencyState.WeekDays(initDays)
            .convertToFrequency()

        @BeforeEach
        fun setUp() {
            model =
                EditTaskFrequencyModel(
                    freq
                )
        }

        @Test
        fun `then freqState is WeekDays and currentDays are monday tuesday`(){
            val expectedDays = initDays
            val expectedState = ReminderFrequencyState.WeekDays(expectedDays)
            val actualState = model.frequencyState
            val actualDays = model.currentWeekDays
            assertEquals(expectedDays,actualDays)
            assertEquals(expectedState,actualState)
        }
        @Nested
        inner class SetDailyFrequency       {


            @Test
            fun `Given 3, then freqState is Daily and currentDailyFreq is 3 `(){
                val expectedFreq = 3
                val expectedState = ReminderFrequencyState.Daily(expectedFreq)
                model.setDailyFrequency(expectedFreq)
                val actualState = model.frequencyState
                val actualFreq = model.currentDailyFrequency
                assertEquals(expectedFreq,actualFreq)
                assertEquals(expectedState,actualState)
            }

        }

        @Nested
        inner class SetWeekDaysFrequency {
            @Test
            fun `Given Monday Friday,then freqState is DaysOfWeek and currentDays are monday friday`() {
                val expectedDays = setOf<DayOfWeekValue>(Day.FRIDAY.value, Day.MONDAY.value)
                val expectedState = ReminderFrequencyState.WeekDays(expectedDays)
                model.setDaysOfWeekFrequency(expectedDays)
                val actualState = model.frequencyState
                val actualDays = model.currentWeekDays
                assertEquals(expectedState, actualState)
                assertEquals(expectedDays, actualDays)
            }

        }




    }
}