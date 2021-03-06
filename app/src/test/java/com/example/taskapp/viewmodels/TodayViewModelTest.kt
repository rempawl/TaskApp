package com.example.taskapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.data.Result
import com.example.taskapp.data.task.TaskMinimal
import com.example.taskapp.dataSources.task.TaskRepository
import com.example.taskapp.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class TodayViewModelTest {

    init {
        loadTimeZone()
    }

    private val dispatcherProvider = TestDispatcherProvider()

    @get:Rule
    val testCoroutineRule = CoroutineTestRule(dispatcherProvider.test)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var taskRepository: TaskRepository

    lateinit var viewModel: TodayViewModel

    @Before
    fun setUp() {
        viewModel = TodayViewModel(FakeTaskRepository(), dispatcherProvider)
    }

    @Test
    fun getTasks() {
        testCoroutineRule.runBlockingTest {
            val res = viewModel.tasks.getOrAwaitValue()
            assert(res.checkIfIsSuccessAndListOf<TaskMinimal>())
            res as Result.Success
            assertThat(res.data,`is`(FakeTaskRepository.todayMinTasks))

        }

    }

}