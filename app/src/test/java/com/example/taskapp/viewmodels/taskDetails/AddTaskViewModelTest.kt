package com.example.taskapp.viewmodels.taskDetails

import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.InstantTaskExecutor
import com.example.taskapp.utils.TestSchedulerProvider
import com.example.taskapp.utils.loadTimeZone
import com.example.taskapp.viewmodels.AddTaskViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach

@ExperimentalCoroutinesApi
class AddTaskViewModelTest {
    init {
        loadTimeZone()
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutor()

    lateinit var viewModel: AddTaskViewModel

    @MockK
    lateinit var taskRepository: TaskRepositoryInterface

    @MockK
    lateinit var taskDetailsModel: TaskDetailsModel

    private val schedulerProvider =        TestSchedulerProvider()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)


    }


    companion object {
        const val TEST_NAME = "test name"
        const val TEST_DESC = "test desc"
    }


}