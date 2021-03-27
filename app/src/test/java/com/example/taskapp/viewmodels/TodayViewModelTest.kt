package com.example.taskapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
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
    val testCoroutineRule = CoroutineTestRule(dispatcherProvider.dispatcher)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


//    @MockK
//    lateinit var taskRepository: TaskRepositoryInterface

    lateinit var viewModel: TodayViewModel

    @Before
    fun setUp(){
//        MockKAnnotations.init(this)
        testCoroutineRule.runBlockingTest {
            viewModel = TodayViewModel(FakeTaskRepository(), dispatcherProvider)
        }
    }

    @Test
    fun `get tasks returns default minimal tasks`(){
        testCoroutineRule.runBlockingTest {
            val actual = viewModel.tasks.getOrAwaitValue()
            assertThat(actual, `is`(expected))

        }

    }

}