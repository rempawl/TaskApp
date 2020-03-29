package com.example.taskapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.getOrAwaitValue
import com.example.taskapp.loadTimeZone
import com.example.taskapp.repos.task.DefaultTasks.errorTask
import com.example.taskapp.repos.task.FakeTaskRepository
import com.example.taskapp.repos.task.TaskRepositoryInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class TaskDetailsViewModelTest {
    init {
        loadTimeZone()
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var taskRepository: TaskRepositoryInterface
    private lateinit var viewModel: TaskDetailsViewModel

    private val taskId: Long = 0

    @BeforeEach
    fun setUp() {
        taskRepository = FakeTaskRepository()
        viewModel = TaskDetailsViewModel(taskId, taskRepository)

    }


    @Test
    fun `getTask gets default task with id equal to taskId `() {
        TestCoroutineScope(TestCoroutineDispatcher()).launch {
            val actualTask = viewModel.task.getOrAwaitValue()
            val expectedTask = taskRepository.getTaskByID(taskId)

            assertThat(actualTask.taskID, `is`(expectedTask.taskID))
            assertThat(actualTask, `is`(expectedTask))
        }
    }

    @Test
    fun ` deleteTask deletes task from repository and sets isTaskDeleted to true`() {

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.deleteTask()

            val actualTask = taskRepository.getTaskByID(taskId)
            val expectedTask = errorTask

            val actualFlag = viewModel.getTaskDeleted().getOrAwaitValue()

            assertTrue(actualFlag)
            assertThat(actualTask, `is`(expectedTask))
        }
    }
}


