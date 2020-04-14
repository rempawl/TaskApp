package com.example.taskapp.viewmodels

import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.InstantTaskExecutor
import com.example.taskapp.utils.loadTimeZone
import com.example.taskapp.viewmodels.reminder.durationModel.EditTaskDurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.EditTaskFrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.EditTaskNotificationModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.LocalDate

class AddReminderViewModelTest {
    init {
        loadTimeZone()

    }

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutor()

    @MockK
    lateinit var taskRepositoryInterface: TaskRepositoryInterface

    @MockK
    lateinit var defaultDurationModelFactory: EditTaskDurationModel.Factory

    @MockK
    lateinit var freqModelFactory: EditTaskFrequencyModel.Factory

    @MockK
    lateinit var defaultNotificationModelFactory: EditTaskNotificationModel.Factory

    @MockK
    lateinit var task: DefaultTask

    lateinit var viewModel: AddReminderViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { defaultNotificationModelFactory.create() } returns EditTaskNotificationModel(
            null
        )
        every { defaultDurationModelFactory.create() } returns EditTaskDurationModel(
            null,
            LocalDate.now()
        )
        every { freqModelFactory.create() } returns EditTaskFrequencyModel(
            null
        )

/*todo
        viewModel = AddReminderViewModel(
            taskRepository = taskRepositoryInterface,
            task = task,
        )
*/

    }

    @Test
    fun `createTask returns `() {

    }

}