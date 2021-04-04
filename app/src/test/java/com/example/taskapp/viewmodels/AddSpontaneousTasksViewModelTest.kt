package com.example.taskapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.data.Result
import com.example.taskapp.data.task.Task
import com.example.taskapp.dataSources.task.TaskRepository
import com.example.taskapp.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AddSpontaneousTasksViewModelTest {

    init {
        loadTimeZone()
    }
    private val dispatcherProvider = TestDispatcherProvider()

    @get:Rule
    val coroutineScope = CoroutineTestRule(dispatcherProvider.test)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AddSpontaneousTasksViewModel

    lateinit var taskRepository: TaskRepository

    @Before
    fun setUp() {
        taskRepository = FakeTaskRepository()
        viewModel = AddSpontaneousTasksViewModel(taskRepository, dispatcherProvider)
    }


    @Test
    fun `get tasks  returns defaultTasks `() {
        coroutineScope.runBlockingTest {
            val res = viewModel.result.getOrAwaitValue()
            assert(res.checkIfIsSuccessAndListOf<Task>())
            res as Result.Success
//            assertThat(res.data, `is`())
        }
    }


}

