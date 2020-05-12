package com.example.taskapp.viewmodels

import com.example.taskapp.repos.task.TaskRepository
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

internal class MyTasksViewModelTest {
    init {
        loadTimeZone()
    }

    private val dispatcherProvider = TestDispatcherProvider()

    @MockK
    private lateinit var taskRepository: TaskRepository

    private lateinit var viewModel: MyTasksViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutor()

    @get:Rule
    val coroutineScope = CoroutineTestRule(dispatcherProvider.provideDefaultDispatcher())

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MyTasksViewModel(taskRepository,dispatcherProvider)
    }

    @Test
    fun `get tasks returns default tasks`() {
        coEvery { taskRepository.getTasks() } returns DefaultTasks.tasks
        coroutineScope.runBlockingTest {
            val actualTasks = viewModel.tasks.getOrAwaitValue()
            val expected = DefaultTasks.minimalTasks

            assertThat(actualTasks,`is`(expected))
        }
    }
}