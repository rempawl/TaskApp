package com.example.taskapp.viewmodels.reminder

import com.example.taskapp.database.entities.NotificationTime
import com.example.taskapp.loadTimeZone
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.threeten.bp.LocalTime

internal class DefaultNotificationModelTest {

    init{
        loadTimeZone()
    }


    lateinit var modelDefault: DefaultNotificationModel

    @Nested
    inner class Init{

        @Test
        fun `given notificationTime null,then  isNotificationTimeSet is false `(){
            modelDefault = DefaultNotificationModel(null)
            assertFalse(modelDefault.isNotificationTimeSet.get() as Boolean)
        }

        @Test
        fun ` given NotificationTime(3,20) then isNotificationTImeSet is true and notificationTIme is Given one`(){
            val expectedTime = LocalTime.of(3,20)
            modelDefault = DefaultNotificationModel(NotificationTime.from(expectedTime))
            val actualFlag = modelDefault.isNotificationTimeSet.get() as Boolean
            val actualTime = modelDefault.notificationTime

            assertTrue(actualFlag)
            assertEquals(expectedTime,actualTime)
        }


    }


    @Nested
    inner class SetNotificationTime {

        @BeforeEach
        fun setUp(){
            modelDefault = DefaultNotificationModel(null)

        }
        @Test
        fun `Given LocalTime(1,25),then  IsNotificationTime is true and notificationTime is given one`() {
                val time = LocalTime.of(1,25)
                modelDefault.setNotificationTime(time)
                val actualTime = modelDefault.notificationTime
                val actualFlag = modelDefault.isNotificationTimeSet.get() as Boolean

                assertThat(actualTime,`is`(time))
                assertTrue(actualFlag)
        }
    }

}