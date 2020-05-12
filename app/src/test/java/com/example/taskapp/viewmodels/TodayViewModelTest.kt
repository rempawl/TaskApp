package com.example.taskapp.viewmodels

import com.example.taskapp.utils.*
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
internal class TodayViewModelTest {

    init {
        loadTimeZone()
    }

    private val dispatcherProvider = TestDispatcherProvider()
    @get:Rule
    val testCoroutineRule = CoroutineTestRule(dispatcherProvider.provideDefaultDispatcher())

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutor()


//    @MockK
//    lateinit var taskRepository: TaskRepositoryInterface

    lateinit var viewModel: TodayViewModel

    @BeforeEach
    fun setUp(){
//        MockKAnnotations.init(this)
        viewModel = TodayViewModel(FakeTaskRepository(),dispatcherProvider)

    }

    @Test
    fun `get tasks returns default minimal tasks`(){
        testCoroutineRule.runBlockingTest {
//            coEvery { taskRepository.getTodayMinTasks() } returns MutableLiveData(DefaultTasks.minimalTasks)

            val actual = viewModel.tasks.getOrAwaitValue()
            val expected = DefaultTasks.minimalTasks

            assertThat(actual, `is`(expected))
        }
    }

}