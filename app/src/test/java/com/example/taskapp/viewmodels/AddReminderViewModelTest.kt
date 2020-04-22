package com.example.taskapp.viewmodels

import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.InstantTaskExecutor
import com.example.taskapp.utils.loadTimeZone
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddReminderViewModelTest {
    init {
        loadTimeZone()

    }

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutor()

    @MockK
    lateinit var taskRepositoryInterface: TaskRepositoryInterface


    lateinit var viewModel: AddReminderViewModel

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)

    }

    @Test
    fun `createTask returns `() {

    }

}