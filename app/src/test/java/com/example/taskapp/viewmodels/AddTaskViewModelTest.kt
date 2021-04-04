package com.example.taskapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.dataSources.task.TaskRepository
import com.example.taskapp.utils.loadTimeZone
import org.junit.Before
import org.junit.Rule

class AddTaskViewModelTest {
    init {
        loadTimeZone()
    }

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    lateinit var taskRepository: TaskRepository

    lateinit var viewModel: AddTaskViewModel

    @Before
    fun setUp() {

    }
}