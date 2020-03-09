package com.example.taskapp.viewmodels

import com.example.taskapp.repos.task.FakeTaskRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TaskDetailsViewModelTest {

    private lateinit var viewModel: TaskDetailsViewModel
    private val taskId: Long = 0

    @BeforeEach
    fun setUp() {
        viewModel = TaskDetailsViewModel(taskId, FakeTaskRepository())
    }

    @Test
    fun `deletes task from repository`() {

    }
}
