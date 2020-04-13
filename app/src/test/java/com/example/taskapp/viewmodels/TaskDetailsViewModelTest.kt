package com.example.taskapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.CoroutineTestRule
import com.example.taskapp.getOrAwaitValue
import com.example.taskapp.loadTimeZone
import com.example.taskapp.repos.task.DefaultTasks
import com.example.taskapp.repos.task.DefaultTasks.errorTask
import com.example.taskapp.repos.task.TaskRepositoryInterface
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@ExperimentalCoroutinesApi
class TaskDetailsViewModelTest {
    init {
        loadTimeZone()
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @MockK
    lateinit var taskRepository: TaskRepositoryInterface

    private lateinit var viewModel: TaskDetailsViewModel

    private val taskId: Long = 0

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = TaskDetailsViewModel(taskId, taskRepository)

    }


    @Test
    fun `getTask gets default task with id equal to taskId `() {
        coEvery { taskRepository.getTaskByID(taskId) } returns DefaultTasks.tasks[taskId.toInt()]

        TestCoroutineScope(TestCoroutineDispatcher()).launch {
            val actualTask = viewModel.task.getOrAwaitValue()
            val expectedTask = taskRepository.getTaskByID(taskId)

            assertThat(actualTask.taskID, `is`(expectedTask.taskID))
            assertThat(actualTask, `is`(expectedTask))
        }
    }

    @Test
    fun ` deleteTask deletes task from repository and sets isTaskDeleted to true`() {
        coEvery { taskRepository.deleteByID(taskId) } returns 1
        TestCoroutineScope(TestCoroutineDispatcher()).launch {
            viewModel.deleteTask()

            val actualTask = taskRepository.getTaskByID(taskId)
            val expectedTask = errorTask

            val actualFlag = viewModel.taskDeleted.getOrAwaitValue()

            assertTrue(actualFlag)
            assertThat(actualTask, `is`(expectedTask))
        }
    }

    @Test
    fun `when deleteTask doesn't delete task from repository exception is thrown`() {
        coEvery { taskRepository.deleteByID(taskId) } returns 0

            assertThrows<IllegalStateException> {
                viewModel.deleteTask()
            }
    }
}

