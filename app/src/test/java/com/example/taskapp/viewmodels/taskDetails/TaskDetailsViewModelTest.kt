package com.example.taskapp.viewmodels.taskDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.data.Result
import com.example.taskapp.data.task.Task
import com.example.taskapp.dataSources.task.TaskRepository
import com.example.taskapp.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TaskDetailsViewModelTest {
    init {
        loadTimeZone()
    }

    private val dispatcherProvider = TestDispatcherProvider()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule(dispatcherProvider.test)

    lateinit var taskRepository: TaskRepository

    private lateinit var viewModel: TaskDetailsViewModel

    private var taskId: Long = 1

    @Before
    fun setUp() {
        taskRepository = FakeTaskRepository()
        viewModel = TaskDetailsViewModel(taskId, taskRepository, dispatcherProvider)

    }


    @Test
    fun getTaskWithReminder() {
        coroutineTestRule.runBlockingTest {
            val result = viewModel.result.getOrAwaitValue()
            assert(result.checkIfIsSuccessAnd<Task>())

            val expected = taskRepository.getTaskByID(taskId).first()
            assertThat(result, `is`(expected))
            val expTask = ((expected as Result.Success).data as Task)

            val reminder = viewModel.reminder.getOrAwaitValue()
            val expReminder = expTask.reminder
            assertThat(reminder,`is`(expReminder))

            val toEdit = viewModel.taskToEdit.getOrAwaitValue ()
            assertThat(toEdit,`is`(expTask))
        }
    }

    @Test
    fun deleteTask() {

        coroutineTestRule.runBlockingTest {
            val value = viewModel.deleteTask()
            assertThat(value, `is`(1))
        }
    }

    @Test
    fun getTaskWithoutReminder() {
        taskId = 0
        viewModel = TaskDetailsViewModel(taskId, taskRepository, dispatcherProvider)

        coroutineTestRule.runBlockingTest {
            val result = viewModel.result.getOrAwaitValue()
            assert(result.checkIfIsSuccessAnd<Task>())

            val expected = taskRepository.getTaskByID(taskId).first()
            assertThat(result, `is`(expected))
            val expTask = ((expected as Result.Success).data as Task)

            val reminder = viewModel.reminder.getOrAwaitValue()
            assert(reminder == null)
            val toEdit = viewModel.taskToEdit.getOrAwaitValue ()
            assertThat(toEdit,`is`(expTask))
        }
    }


}

