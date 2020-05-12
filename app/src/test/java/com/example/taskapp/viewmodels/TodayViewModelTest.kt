package com.example.taskapp.viewmodels

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
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class TodayViewModelTest{
init {
    loadTimeZone()
}

    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutor()

    @MockK
    lateinit var taskRepository: TaskRepository
    lateinit var viewModel: TodayViewModel

    @BeforeEach
    fun setUp(){
        MockKAnnotations.init(this)
        viewModel = TodayViewModel(taskRepository)
    }

    @Test
    fun `get tasks returns default minimal tasks`(){
        coEvery { taskRepository.getTodayMinTasks() } returns MutableLiveData(DefaultTasks.minimalTasks)
        testCoroutineRule.runBlockingTest {
            val actual = viewModel.tasks.getOrAwaitValue()
            val expected = DefaultTasks.minimalTasks
            assertThat(actual, `is`(expected))
        }
    }

}