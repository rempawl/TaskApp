package com.example.taskapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.database.entities.Task
import com.example.taskapp.getOrAwaitValue
import com.example.taskapp.loadTimeZone
import com.example.taskapp.repos.task.DefaultTasks.tasks
import com.example.taskapp.repos.task.TaskRepositoryInterface
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
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
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    lateinit var viewModel: AddSpontaneousTasksViewModel

    private val defaultTasks = tasks.toList()

    @MockK
    lateinit var taskRepository: TaskRepositoryInterface

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = AddSpontaneousTasksViewModel(taskRepository)
    }

    @Test
    fun `when taskRepository returns emptyList `() {
        TestCoroutineScope().launch {
            val expectedValue = emptyList<Task>()
            coEvery { taskRepository.getNotTodayTasks() } returns emptyList()
            val actualValue = viewModel.tasks.getOrAwaitValue()
            assertThat(actualValue, `is`(expectedValue))
        }

    }

    @Test
    fun `when taskRepository returns defaultList `() {
        TestCoroutineScope().launch {
            val expectedValue = defaultTasks
            coEvery { taskRepository.getNotTodayTasks() } returns defaultTasks
            val actualValue = viewModel.tasks.getOrAwaitValue()
            assertThat(actualValue, `is`(expectedValue))
        }
    }


}


