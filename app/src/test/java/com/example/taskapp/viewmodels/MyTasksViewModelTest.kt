package com.example.taskapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.data.Result
import com.example.taskapp.data.task.TaskMinimal
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
 class MyTasksViewModelTest {
    init {
        loadTimeZone()
    }

    private val dispatcherProvider = TestDispatcherProvider()

    private lateinit var taskRepository: TaskRepository

    private lateinit var viewModel: MyTasksViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = CoroutineTestRule(dispatcherProvider.test)

    @Before
    fun setUp() {
        taskRepository = FakeTaskRepository()
        viewModel = MyTasksViewModel(taskRepository, dispatcherProvider)
    }

    @Test
    fun getTasks() {
        coroutineScope.runBlockingTest {
            val res = viewModel.result.getOrAwaitValue()
            assert(res.checkIfIsSuccessAndListOf<TaskMinimal>())
            res as Result.Success
            val expected = FakeTaskRepository.minTasks
            assertThat(res.data, `is`(expected))
        }
    }
}