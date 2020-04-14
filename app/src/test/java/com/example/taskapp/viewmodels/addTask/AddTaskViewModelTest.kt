package com.example.taskapp.viewmodels.addTask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.loadTimeZone
import com.example.taskapp.repos.task.TaskRepositoryInterface
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = AddTaskViewModel(taskDetailsModel, taskRepository)
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