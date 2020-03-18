package com.example.taskapp.viewmodels.addTask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.loadTimeZone
import com.example.taskapp.repos.task.FakeTaskRepository
import com.example.taskapp.repos.task.TaskRepositoryInterface
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach

class AddTaskViewModelTest {
    init {
        loadTimeZone()
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: AddTaskViewModel
    private val defaultTasks = FakeTaskRepository.tasks.toList()
    @MockK
    lateinit var taskRepository: TaskRepositoryInterface
    @MockK
    lateinit var taskDetailsModel: TaskDetailsModel

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = AddTaskViewModel(taskDetailsModel,taskRepository)

    }



}