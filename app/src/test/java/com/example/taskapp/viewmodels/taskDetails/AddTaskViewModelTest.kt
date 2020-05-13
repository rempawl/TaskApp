package com.example.taskapp.viewmodels.taskDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.TestSchedulerProvider
import com.example.taskapp.utils.loadTimeZone
import com.example.taskapp.viewmodels.AddTaskViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
class AddTaskViewModelTest {
    init {
        loadTimeZone()
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: AddTaskViewModel

    @MockK
    lateinit var taskRepository: TaskRepositoryInterface

    @MockK
    lateinit var taskDetailsModel: TaskDetailsModel

    private val schedulerProvider = TestSchedulerProvider()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

    }


    companion object {
        const val TEST_NAME = "test name"
        const val TEST_DESC = "test desc"
    }


}