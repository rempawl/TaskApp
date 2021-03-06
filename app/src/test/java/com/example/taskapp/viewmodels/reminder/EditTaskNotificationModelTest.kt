package com.example.taskapp.viewmodels.reminder

import com.example.taskapp.data.reminder.NotificationTime
import com.example.taskapp.utils.loadTimeZone
import com.example.taskapp.viewmodels.reminder.notificationModel.EditTaskNotificationModel
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.threeten.bp.LocalTime

internal class EditTaskNotificationModelTest {

    init{
        loadTimeZone()
    }

    lateinit var notificationModel: EditTaskNotificationModel

    @Nested
    inner class Init{

        @Test
        fun `given notificationTime null,then  isNotificationTimeSet is false `(){
            notificationModel =
                EditTaskNotificationModel(
                    null
                )
            assertFalse(notificationModel.isNotificationTimeSet.get() as Boolean)
        }

        @Test
        fun ` given NotificationTime(3,20) then isNotificationTImeSet is true and notificationTIme is Given one`(){
            val expectedTime = LocalTime.of(3,20)
            notificationModel =
                EditTaskNotificationModel(
                    NotificationTime.from(expectedTime)
                )
            val actualFlag = notificationModel.isNotificationTimeSet.get() as Boolean
            val actualTime = notificationModel.notificationTime

            assertTrue(actualFlag)
            assertEquals(expectedTime,actualTime)
        }


    }


    @Nested
    inner class SetNotificationTime {

        @BeforeEach
        fun setUp(){
            notificationModel =
                EditTaskNotificationModel(
                    null
                )

        }
        @Test
        fun `Given LocalTime(1,25),then  IsNotificationTime is true and notificationTime is given one`() {
                val time = LocalTime.of(1,25)
                notificationModel.notificationTime = (time)
                val actualTime = notificationModel.notificationTime
                val actualFlag = notificationModel.isNotificationTimeSet.get() as Boolean

                assertThat(actualTime,`is`(time))
                assertTrue(actualFlag)
        }
    }

}