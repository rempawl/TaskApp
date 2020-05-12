package com.example.taskapp.viewmodels

import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.*
import com.example.taskapp.utils.DefaultTasks.tasks
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
class AddSpontaneousTasksViewModelTest {

    init {
        loadTimeZone()
    }
    private val dispatcherProvider = TestDispatcherProvider()

    @get:Rule
    val coroutineScope = CoroutineTestRule(dispatcherProvider.provideDefaultDispatcher())

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutor()

    private lateinit var viewModel: AddSpontaneousTasksViewModel

    @MockK
    lateinit var taskRepository: TaskRepositoryInterface

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = AddSpontaneousTasksViewModel(taskRepository,dispatcherProvider)
    }


    @Test
    fun `get tasks  returns defaultTasks `() {
        coEvery { taskRepository.getNotTodayTasks() } returns tasks

        coroutineScope.runBlockingTest {
            val expectedValue = tasks.toList()
            val actualValue = viewModel.tasks.getOrAwaitValue()
            assertThat(actualValue, `is`(expectedValue))
        }
    }


}

