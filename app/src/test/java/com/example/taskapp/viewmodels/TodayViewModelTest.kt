package com.example.taskapp.viewmodels

import com.example.taskapp.repos.task.DefaultTasks
import com.example.taskapp.repos.task.TaskRepository
import com.example.taskapp.utils.InstantTaskExecutor
import com.example.taskapp.utils.getOrAwaitValue
import com.example.taskapp.utils.loadTimeZone
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TodayViewModelTest{
init {
    loadTimeZone()
}

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutor()

    @MockK
    lateinit var taskRepository: TaskRepository
    lateinit var viewModel: TodayViewModel

    @BeforeEach
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