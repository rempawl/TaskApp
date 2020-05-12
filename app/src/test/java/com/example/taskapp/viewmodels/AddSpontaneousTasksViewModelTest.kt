package com.example.taskapp.viewmodels

import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.CoroutineTestRule
import com.example.taskapp.utils.DefaultTasks.tasks
import com.example.taskapp.utils.InstantTaskExecutor
import com.example.taskapp.utils.getOrAwaitValue
import com.example.taskapp.utils.loadTimeZone
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

@ExperimentalCoroutinesApi
class AddSpontaneousTasksViewModelTest {

    init {
        loadTimeZone()
    }

    @get:Rule
    val coroutineScope = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutor()

    private lateinit var viewModel: AddSpontaneousTasksViewModel

    private val defaultTasks = tasks.toList()

    @MockK
    lateinit var taskRepository: TaskRepositoryInterface

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = AddSpontaneousTasksViewModel(taskRepository)
    }


    @Test
    fun `get tasks  returns defaultTasks `() {
        coEvery { taskRepository.getNotTodayTasks() } returns defaultTasks

        coroutineScope.runBlockingTest {
            val expectedValue = defaultTasks
            val actualValue = viewModel.tasks.getOrAwaitValue()
            assertThat(actualValue, `is`(expectedValue))
        }
    }


}

