package com.example.taskapp.viewmodels.addTask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.R
import com.example.taskapp.database.entities.Task
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class TaskDetailsModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    lateinit var taskDetailsModel: TaskDetailsModel

    @BeforeEach
    fun setUp() {
        taskDetailsModel = TaskDetailsModel()
    }

    @DisplayName("when taskName is `TEST_NAME` and taskDesc is `TEST_DESC` ")
    @Nested
    inner class TaskNameValidNameTaskDescDefault {
        @Test
        fun `createTaskDetails returns TaskDetails(TEST_NAME,TEST_DESC) `() {
            val expectedName = TEST_NAME
            val expectedDesc = TEST_DESC
            val expectedValue = TaskDetails(expectedName, expectedDesc)

            taskDetailsModel.taskName = TEST_NAME
            taskDetailsModel.taskDescription = TEST_DESC

            val actualValue = taskDetailsModel.createTaskDetails()
            assertThat(actualValue, `is`(expectedValue))
        }


        @Test
        fun `createTask returns Task(name=TEST_NAME,desc=TEST_DESC) `() {
            val expectedName = TEST_NAME
            val expectedDesc = TEST_DESC
            val expectedValue = Task(name = expectedName, description = expectedDesc)

            taskDetailsModel.taskName = TEST_NAME
            taskDetailsModel.taskDescription = TEST_DESC

            val actualValue = taskDetailsModel.createTask()
            assertThat(actualValue, `is`(expectedValue))
        }


        @Test
        fun `isTaskNameValid returns true taskNameError is null`() {
            taskDetailsModel.taskName = TEST_NAME

            val actualValue = taskDetailsModel.isTaskNameValid(false)
            val actualError = taskDetailsModel.taskNameError.get()

            assertTrue(actualValue)
            assertNull(actualError)
        }

        @Test
        fun ` isValid returns true taskNameError is null`() {
            taskDetailsModel.taskName = TEST_NAME

            val actualValue = taskDetailsModel.isValid()
            val actualError = taskDetailsModel.taskNameError.get()

            assertTrue(actualValue)
            assertNull(actualError)
        }


    }

    @DisplayName("When taskName equals INVALID_NAME")
    @Nested
    inner class InvalidTaskName {
        @Test
        fun `Then isValid returns false, taskNameError is  null`() {
            taskDetailsModel.taskName = INVALID_NAME

            val expectedError = R.string.task_name_error
            val actualValue = taskDetailsModel.isValid()
            val actualError = taskDetailsModel.taskNameError.get()

            assertFalse(actualValue)
            assertNull(actualError)


        }

        @Test
        fun ` Given setMessage true,Then isValid returns false, taskNameError is set to  task_name_error resourceID`() {
            taskDetailsModel.taskName = INVALID_NAME

            val expectedError = R.string.task_name_error
            val actualValue = taskDetailsModel.isTaskNameValid(true)
            val actualError = taskDetailsModel.taskNameError.get()

            assertFalse(actualValue)
            assertThat(actualError, `is`(expectedError))


        }

        @Test
        fun ` Given setMessage false,Then isValid returns false, taskNameError is null`() {
            taskDetailsModel.taskName = INVALID_NAME

            val actualValue = taskDetailsModel.isTaskNameValid(false)
            val actualError = taskDetailsModel.taskNameError.get()

            assertFalse(actualValue)
            assertNull(actualError)

        }


    }

    @Test
    fun `setTaskName to TEST_NAME, Then taskName equals TEST_NAME  `() {
        taskDetailsModel.taskName = TEST_NAME
        val expectedValue = TEST_NAME
        val actualValue = taskDetailsModel.taskName
        assertThat(actualValue, `is`(expectedValue))
    }


    @Test
    fun ` setTaskDescription to TASK_DESC, Then TaskDescription equals TEST_DESC`() {
        taskDetailsModel.taskDescription = TEST_DESC
        val expectedValue = TEST_DESC
        val actualValue = taskDetailsModel.taskDescription
        assertThat(actualValue, `is`(expectedValue))

    }




    companion object {
        const val TEST_NAME = "test name"
        const val TEST_DESC = "test desc"
        const val INVALID_NAME = "22"
    }
}