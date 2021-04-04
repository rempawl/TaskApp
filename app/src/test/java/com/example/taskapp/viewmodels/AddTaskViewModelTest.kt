package com.example.taskapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.dataSources.task.TaskRepository
import com.example.taskapp.utils.FakeTaskRepository
import com.example.taskapp.utils.loadTimeZone
import com.example.taskapp.viewmodels.reminder.durationModel.DurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.NotificationModel
import com.example.taskapp.viewmodels.taskDetails.TaskDetailsModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock

class AddTaskViewModelTest {
    init {
        loadTimeZone()
    }

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    lateinit var taskRepository: TaskRepository

    lateinit var viewModel: AddTaskViewModel
    lateinit var durationModel: DurationModel
    lateinit var frequencyModel: FrequencyModel
    lateinit var notificationModel: NotificationModel

    lateinit var detailsModel: TaskDetailsModel

    @Before
    fun setUp() {
        taskRepository = FakeTaskRepository()
        durationModel = mock {  }
        frequencyModel = mock {  }
        notificationModel = mock {  }
        detailsModel = mock {  }

        viewModel = AddTaskViewModel(detailsModel,taskRepository,durationModel,notificationModel,frequencyModel)
    }
    @Test
    fun init(){

    }
}