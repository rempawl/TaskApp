package com.example.taskapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.taskapp.repos.task.TaskRepository
import com.example.taskapp.utils.*
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

internal class MyTasksViewModelTest {
    init {
        loadTimeZone()
    }

    private val dispatcherProvider = TestDispatcherProvider()

    @MockK
    private lateinit var taskRepository: TaskRepository

    private lateinit var viewModel: MyTasksViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = CoroutineTestRule(dispatcherProvider.dispatcher)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MyTasksViewModel(taskRepository, dispatcherProvider)
    }

    @Test
    fun `get tasks returns default tasks`() {
        coEvery { taskRepository.getMinimalTasks() } returns  MutableLiveData(DefaultTasks.minimalTasks)
        coroutineScope.runBlockingTest {

            val actualTasks = viewModel.tasks.getOrAwaitValue()
            val expected = DefaultTasks.minimalTasks

            assertThat(actualTasks,`is`(expected))
        }
    }
}