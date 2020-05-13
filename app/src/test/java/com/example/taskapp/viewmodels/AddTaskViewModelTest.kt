package com.example.taskapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.loadTimeZone
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule

class AddTaskViewModelTest {
    init {
        loadTimeZone()

    }

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @MockK
    lateinit var taskRepositoryInterface: TaskRepositoryInterface


    lateinit var viewModel: AddTaskViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

    }
//todo

}