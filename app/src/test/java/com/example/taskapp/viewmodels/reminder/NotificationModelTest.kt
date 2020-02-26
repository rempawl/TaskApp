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

internal class NotificationModelTest {

    init{
        loadTimeZone()
    }


    lateinit var model: NotificationModel

    @Nested
    inner class Init{

        @Test
        fun `given notificationTime null,then  isNotificationTimeSet is false `(){
            model = NotificationModel(null)
            assertFalse(model.isNotificationTimeSet)
        }

        @Test
        fun ` given NotificationTime(3,20) then isNotificationTImeSet is true and notificationTIme is Given one`(){
            val expectedTime = LocalTime.of(3,20)
            model = NotificationModel(NotificationTime.from(expectedTime))
            val actualFlag = model.isNotificationTimeSet
            val actualTime = model.notificationTime

            assertTrue(actualFlag)
            assertEquals(expectedTime,actualTime)
        }


    }


    @Nested
    inner class SetNotificationTime {

        @BeforeEach
        fun setUp(){
            model = NotificationModel(null)

        }
        @Test
        fun `Given LocalTime(1,25),then  IsNotificationTime is true and notificationTime is given one`() {
                val time = LocalTime.of(1,25)
                model.setNotificationTime(time)
                val actualTime = model.notificationTime
                val actualFlag = model.isNotificationTimeSet

                assertThat(actualTime,`is`(time))
                assertTrue(actualFlag)
        }
    }

}