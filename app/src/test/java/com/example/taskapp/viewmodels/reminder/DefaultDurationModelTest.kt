package com.example.taskapp.viewmodels.reminder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.loadTimeZone
import com.example.taskapp.utils.reminder.ReminderDurationState
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.threeten.bp.LocalDate

internal class DefaultDurationModelTest {
    init {
        loadTimeZone()
    }

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    private lateinit var modelDefault: DefaultDurationModel

    private val today = LocalDate.now()


    @Nested
    @DisplayName("When initial Duration is DaysDuration(15) and begDate is 20,03,2000")
    inner class InitWithDailyDuration {
        private val duration = ReminderDurationState.DaysDuration(15).convertToDuration()
        private val begDate: LocalDate = LocalDate.of(2000, 3, 20)

        @BeforeEach
        fun setUp() {
            modelDefault = DefaultDurationModel(duration, begDate)
        }

        @Test
        fun `then durationState is DaysDuration, currentDaysDuration is 15`() {
            val actualDays = modelDefault.currentDaysDuration
            val actualState = modelDefault.durationState
            val expectedDays = 15
            val expectedState = ReminderDurationState.DaysDuration(expectedDays)

            assertEquals(expectedDays, actualDays)
            assertEquals(expectedState, actualState)
        }

        @Nested
        inner class SetEndDateDurationState {
            @Test
            fun `Given date(25,03,2000), Then endDateError is true,   currentEndDate remains the same`() {
                val expectedDate = modelDefault.currentEndDate
                val date = LocalDate.of(2000, 3, 25)

                modelDefault.setEndDateDurationState(date)

                val isErrorSet = modelDefault.endDateError.get()
                val actualDate = modelDefault.currentEndDate
                assertTrue(isErrorSet!!)
                assertEquals(expectedDate, actualDate)
            }

            @Test
            fun `Given date(25,03,2034),then durState is EndDate and currentEndDate is given date`() {
                val expectedDate = LocalDate.of(2034, 3, 25)
                val expectedState = ReminderDurationState.EndDate(expectedDate)

                modelDefault.setEndDateDurationState(expectedDate)

                val actualDate = modelDefault.currentEndDate
                val actualState = modelDefault.durationState

                assertEquals(expectedDate, actualDate)
                assertEquals(expectedState, actualState)

            }
        }

        @Nested
        inner class SetNoEndDateDurationState {
            @Test
            fun `Then durState is NoEndDate`() {
                val expectedState = ReminderDurationState.NoEndDate
                modelDefault.setNoEndDateDurationState()
                val actualState = modelDefault.durationState

                assertEquals(expectedState, actualState)
            }
        }

    }

    @Nested
    @DisplayName("When initial Duration is null and begDate is today")
    inner class InitWithNullDuration {


        @BeforeEach
        fun setUp() {
            modelDefault = DefaultDurationModel(null, today)
        }

        @Test
        fun `then durState is NoEndDate `() {
            val expectedState = ReminderDurationState.NoEndDate

            val actualState = modelDefault.durationState

            assertEquals(expectedState, actualState)
        }

        @Nested
        inner class SetBeginningDate {

            //when beginning date is valid
            @Test
            fun `When date is tomorrow, Then beginning date is set to given date`() {
                val expectedDate = LocalDate.ofEpochDay(today.toEpochDay() + 1)

                modelDefault.beginningDate = expectedDate

                val actualDate = modelDefault.beginningDate

                assertEquals(expectedDate, actualDate)
            }

            //when beginningDate is before today
            @Test
            fun `When date is yesterday,Then begDateError is true and begDate remains the same`() {

                val expectedDate = modelDefault.beginningDate
                val date = LocalDate.ofEpochDay(today.toEpochDay() - 1)

                modelDefault.beginningDate = date

                val isErrorSet = modelDefault.begDateError.get()!!
                val actualDate = modelDefault.beginningDate

                assertTrue(isErrorSet)
                assertEquals(expectedDate, actualDate)
            }

            //when beginningDate is after endDate
            @Test
            fun `When durState is EndDate(25,03,2030) and date is (4,4,2030),THen begDateError is true and begDate remains the same`() {
                val endDate = LocalDate.of(2030, 3, 25)
                modelDefault.setEndDateDurationState(endDate)
                val date = LocalDate.of(2030, 4, 4)

                val expectedDate = modelDefault.beginningDate

                modelDefault.beginningDate = date

                val isErrorSet = modelDefault.begDateError.get()
                val actualDate = modelDefault.beginningDate

                assertEquals(expectedDate, actualDate)
                assertTrue(isErrorSet!!)

            }


        }


        @Nested
        inner class SetEndDateDurationState {
            @Test
            fun `Given date 10 days before Today, Then endDateError is true and currentEndDate remains the same`() {
                val date = LocalDate.ofEpochDay(today.toEpochDay() - 10)
                val expectedDate = modelDefault.currentEndDate

                modelDefault.setEndDateDurationState(date)

                val isErrorSet = modelDefault.endDateError.get()!!
                val actualDate = modelDefault.currentEndDate

                assertTrue(isErrorSet)
                assertEquals(expectedDate, actualDate)

            }

            @Test
            fun `Given date 10 days after Today, Then durState is EndDate and currentEndDate is given one`(){
                val expectedDate = LocalDate.ofEpochDay(today.toEpochDay()+10)
                val expectedState = ReminderDurationState.EndDate(expectedDate)

                modelDefault.setEndDateDurationState(expectedDate)

                val actualDate = modelDefault.currentEndDate
                val actualState = modelDefault.durationState

                assertEquals(expectedDate,actualDate)
                assertEquals(expectedState,actualState)
            }
        }

        @Nested
        inner class SetDaysDurationState{
            @Test
            fun `Given days eq 10, Then durState is DaysDuration and currentDays eq 10`(){
                val expectedDays = 10
                val expectedState = ReminderDurationState.DaysDuration(expectedDays)

                modelDefault.setDaysDurationState(expectedDays)

                val actualDays = modelDefault.currentDaysDuration
                val actualState = modelDefault.durationState

                assertEquals(expectedDays,actualDays)
                assertEquals(expectedState,actualState)
            }
        }
    }
}