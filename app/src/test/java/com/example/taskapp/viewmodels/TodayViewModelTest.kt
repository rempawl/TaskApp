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

internal class TodayViewModelTest{
init {
    loadTimeZone()
}

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var taskRepository: TaskRepository
    lateinit var viewModel: TodayViewModel

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        viewModel = TodayViewModel(taskRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get tasks returns default minimal tasks`(){
        coEvery { taskRepository.getTodayMinTasks() } returns DefaultTasks.minimalTasks
        TestCoroutineScope(TestCoroutineDispatcher()).launch {
            val actual = viewModel.tasks.getOrAwaitValue()
            val expected = DefaultTasks.minimalTasks
            assertThat(actual, `is`(expected))
        }
    }

}