package com.example.taskapp.viewmodels.addTask

import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.InstantTaskExecutor
import com.example.taskapp.utils.TestSchedulerProvider
import com.example.taskapp.utils.loadTimeZone
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class AddTaskViewModelTest {
    init {
        loadTimeZone()
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutor()

    lateinit var viewModel: DefaultAddTaskViewModel

    @MockK
    lateinit var taskRepository: TaskRepositoryInterface

    @MockK
    lateinit var taskDetailsModel: TaskDetailsModel

    private val schedulerProvider = TestSchedulerProvider()
    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = DefaultAddTaskViewModel(schedulerProvider,taskDetailsModel, taskRepository)
    }

    @Test
    fun `save Task  `() {
        //todo exception
        TestCoroutineScope().launch {
            val newTask = DefaultTask(name = TEST_NAME, description = TEST_DESC)
            coEvery { taskDetailsModel.createTask() } returns newTask
            coEvery { taskRepository.saveTask(taskDetailsModel.createTask()) } returns Single.just(1)
            viewModel.saveTask()
        }


    }

    companion object {
        const val TEST_NAME = "test name"
        const val TEST_DESC = "test desc"
    }


}