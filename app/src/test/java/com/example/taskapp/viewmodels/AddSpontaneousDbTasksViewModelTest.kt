package com.example.taskapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.repos.task.TaskRepository
import com.example.taskapp.utils.CoroutineTestRule
import com.example.taskapp.utils.DefaultTasks.tasks
import com.example.taskapp.utils.TestDispatcherProvider
import com.example.taskapp.utils.getOrAwaitValue
import com.example.taskapp.utils.loadTimeZone
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
class AddSpontaneousDbTasksViewModelTest {

    init {
        loadTimeZone()
    }
    private val dispatcherProvider = TestDispatcherProvider()

    @get:Rule
    val coroutineScope = CoroutineTestRule(dispatcherProvider.dispatcher)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AddSpontaneousTasksViewModel

    @MockK
    lateinit var taskRepository: TaskRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = AddSpontaneousTasksViewModel(taskRepository,dispatcherProvider)
    }


    @Test
    fun `get tasks  returns defaultTasks `() {
        coEvery { taskRepository.getNotTodayTasks() } returns tasks

        coroutineScope.runBlockingTest {
            val expectedValue = tasks.toList()
            val actualValue = viewModel.result.getOrAwaitValue()
            assertThat(actualValue, `is`(expectedValue))
        }
    }


}

