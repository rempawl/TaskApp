package com.example.taskapp.viewmodels

import com.example.taskapp.data.task.Task
import com.example.taskapp.dataSources.task.TaskRepository
import com.example.taskapp.utils.DateUtils.TODAY
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import com.example.taskapp.viewmodels.reminder.durationModel.EditTaskDurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.EditTaskFrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.EditTaskNotificationModel
import com.example.taskapp.viewmodels.taskDetails.TaskDetailsModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

//binding.apply { TODO
//    durationRadioGroup.run {
//        when (this@EditTaskBindingArranger.viewModel.durationModel.durationState) {
//            is ReminderDurationState.NoEndDate -> check(noEndDateRadio.id)
//            is ReminderDurationState.DaysDuration -> check(xDaysDurationRadio.id)
//            is ReminderDurationState.EndDate -> check(endDateRadio.id)
//        }
//    }
//    frequencyRadioGroup.run {
//        when (this@EditTaskBindingArranger.viewModel.frequencyModel.frequencyState) {
//            is ReminderFrequencyState.Daily -> check(dailyFreqRadio.id)
//            is ReminderFrequencyState.WeekDays -> check(daysOfWeekRadio.id)
//        }
//    }
//}

class EditTaskViewModel @AssistedInject constructor(
    taskDetailsModel: TaskDetailsModel,
    @Assisted task: Task,
    private val taskRepository: TaskRepository,
    durationModelFactory: EditTaskDurationModel.Factory,
    frequencyModelFactory: EditTaskFrequencyModel.Factory,
    defaultNotificationModelFactory: EditTaskNotificationModel.Factory,
) : ReminderViewModel(
    taskDetailsModel = taskDetailsModel,
    task = task,
    durationModel = durationModelFactory.create(
        task.reminder?.duration,
        task.reminder?.begDate ?: TODAY
    ),
    frequencyModel = frequencyModelFactory.create(task.reminder?.frequency),
    notificationModel = defaultNotificationModelFactory.create(notificationTime = task.reminder?.notificationTime)

) {

    @AssistedFactory
    interface Factory {
        fun create(task: Task): EditTaskViewModel
    }


    init {
        taskDetailsModel.apply {
            taskDescription = task.description
            taskName = task.name
        }
    }

    override suspend fun addTask(task: Task) {
        taskRepository.updateTask(task)
    }


}




