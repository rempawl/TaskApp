package com.example.taskapp.viewmodels

import com.example.taskapp.loadTimeZone
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.viewmodels.addTask.TaskDetails
import com.example.taskapp.viewmodels.reminder.DefaultDurationModel
import com.example.taskapp.viewmodels.reminder.DefaultNotificationModel
import com.example.taskapp.viewmodels.reminder.FrequencyModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.threeten.bp.LocalDate

class AddReminderViewModelTest {
    init {
        loadTimeZone()
    }

    @MockK
    lateinit var taskRepositoryInterface: TaskRepositoryInterface

    @MockK
    lateinit var defaultDurationModelFactory: DefaultDurationModel.Factory

    @MockK
    lateinit var freqModelFactory: FrequencyModel.Factory

    @MockK
    lateinit var defaultNotificationModelFactory: DefaultNotificationModel.Factory

    @MockK
    lateinit var taskDetails: TaskDetails

    lateinit var viewModel: AddReminderViewModel

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        every { defaultNotificationModelFactory.create() } returns DefaultNotificationModel(null)
        every { defaultDurationModelFactory.create() } returns DefaultDurationModel(null, LocalDate.now())
        every { freqModelFactory.create() } returns FrequencyModel(null)

        viewModel = AddReminderViewModel(
            taskRepository = taskRepositoryInterface,
            taskDetails = taskDetails,
            defaultDurationModelFactory = defaultDurationModelFactory,
            defaultNotificationModelFactory = defaultNotificationModelFactory,
            frequencyModelFactory = freqModelFactory
        )

    }

    @Test
    fun `createTask returns `() {
    }

}