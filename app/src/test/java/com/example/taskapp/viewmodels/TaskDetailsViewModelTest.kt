package com.example.taskapp.viewmodels

import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantTaskExecutor::class)

class TaskDetailsViewModelTest {
    init {
        loadTimeZone()
    }
    private val dispatcherProvider = TestDispatcherProvider()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutor()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule(dispatcherProvider.provideDefaultDispatcher())

    @MockK
    lateinit var taskRepository: TaskRepositoryInterface

    private lateinit var viewModel: TaskDetailsViewModel

    private val taskId: Long = 0

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = TaskDetailsViewModel(taskId, taskRepository,dispatcherProvider)

    }


    @Test
    fun `getTask gets default task with id equal to taskId `() {
        coEvery { taskRepository.getTaskByID(taskId) } returns DefaultTasks.tasks[taskId.toInt()]

        coroutineTestRule.runBlockingTest {
            val actualTask = viewModel.task.getOrAwaitValue()
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

