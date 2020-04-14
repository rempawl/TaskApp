package com.example.taskapp.viewmodels

import com.example.taskapp.repos.task.DefaultTasks
import com.example.taskapp.repos.task.DefaultTasks.errorTask
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.CoroutineTestRule
import com.example.taskapp.utils.InstantTaskExecutor
import com.example.taskapp.utils.getOrAwaitValue
import com.example.taskapp.utils.loadTimeZone
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class TaskDetailsViewModelTest {
    init {
        loadTimeZone()
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutor()
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


}

