package com.example.taskapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.getOrAwaitValue
import com.example.taskapp.loadTimeZone
import com.example.taskapp.repos.task.DefaultTasks
import com.example.taskapp.repos.task.TaskRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class MyTasksViewModelTest {
    init {
        loadTimeZone()
    }

    @MockK
    private lateinit var taskRepository: TaskRepository

    private lateinit var viewModel: MyTasksViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MyTasksViewModel(taskRepository)
    }

    @Test
    fun `get tasks returns default tasks`() {
        coEvery { taskRepository.getTasks() } returns DefaultTasks.tasks
        TestCoroutineScope(TestCoroutineDispatcher()).launch {
            val actualTasks = viewModel.tasks.getOrAwaitValue()
            val expected = DefaultTasks.minimalTasks

            assertThat(actualTasks,`is`(expected))
        }
    }
}