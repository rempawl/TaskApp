package com.example.taskapp.viewmodels

import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.loadTimeZone
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.viewmodels.reminder.durationModel.DefaultDurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.DefaultFrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.DefaultNotificationModel
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
    lateinit var freqModelFactory: DefaultFrequencyModel.Factory

    @MockK
    lateinit var defaultNotificationModelFactory: DefaultNotificationModel.Factory

    @MockK
    lateinit var task: DefaultTask

    lateinit var viewModel: AddReminderViewModel

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        every { defaultNotificationModelFactory.create() } returns DefaultNotificationModel(
            null
        )
        every { defaultDurationModelFactory.create() } returns DefaultDurationModel(
            null,
            LocalDate.now()
        )
        every { freqModelFactory.create() } returns DefaultFrequencyModel(
            null
        )

        viewModel = AddReminderViewModel(
            taskRepository = taskRepositoryInterface,
            task = task,
            defaultDurationModelFactory = defaultDurationModelFactory,
            defaultNotificationModelFactory = defaultNotificationModelFactory,
            frequencyModelFactory = freqModelFactory
        )

    }

    @Test
    fun `createTask returns `() {

    }

}