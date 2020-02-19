package com.example.taskapp.viewmodels

import com.example.taskapp.repos.task.TaskRepository
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

internal class MyTasksViewModelTest {

    private lateinit var viewModel: MyTasksViewModel
    private lateinit var taskRepository: TaskRepository
    @Before
    fun setUp() {
        taskRepository = mockk()
        viewModel = mockk()
    }

    @Test
    fun getTasks()  {
//        every { taskRepository.getTasks() } returns
    }


    @Test
    fun setTasks() {
    }
}