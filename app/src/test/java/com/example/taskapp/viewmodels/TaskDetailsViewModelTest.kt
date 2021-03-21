package com.example.taskapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.repos.task.TaskRepository
import com.example.taskapp.utils.*
import com.example.taskapp.viewmodels.taskDetails.TaskDetailsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TaskDetailsViewModelTest {
    init {
        loadTimeZone()
    }
    private val dispatcherProvider = TestDispatcherProvider()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule(dispatcherProvider.dispatcher)

    @MockK
    lateinit var taskRepository: TaskRepository

    private lateinit var viewModel: TaskDetailsViewModel

    private val taskId: Long = 0

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = TaskDetailsViewModel(taskId, taskRepository,dispatcherProvider)

    }


    @Test
    fun `getTask gets default task with id equal to taskId `() {
        coEvery { taskRepository.getTaskByID(taskId) } returns DefaultTasks.tasks[taskId.toInt()]

        coroutineTestRule.runBlockingTest {
            val actualTask = viewModel.result.getOrAwaitValue()
            val expectedTask = taskRepository.getTaskByID(taskId)

            assertThat(actualTask.taskID, `is`(expectedTask.taskID))
            assertThat(actualTask, `is`(expectedTask))
        }
    }

    @Test
    fun ` deleteTask deletes task from repository`() {
        coEvery { taskRepository.deleteByID(taskId) } returns 1

        coroutineTestRule.runBlockingTest {
            val value = viewModel.deleteTask()
            assertThat(value, `is`(1))
        }
    }


}

