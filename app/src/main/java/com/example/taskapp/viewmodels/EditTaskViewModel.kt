package com.example.taskapp.viewmodels

import com.example.taskapp.database.entities.Task
import com.example.taskapp.viewmodels.addTask.TaskFields
import com.example.taskapp.viewmodels.reminder.DurationModel
import com.example.taskapp.viewmodels.reminder.FrequencyModel
import com.example.taskapp.viewmodels.reminder.NotificationModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class EditTaskViewModel @AssistedInject constructor(
    @Assisted val task: Task,
    val taskFields: TaskFields,
    durationModelFactory: DurationModel.Factory,
    frequencyModelFactory: FrequencyModel.Factory,
    notificationModelFactory: NotificationModel.Factory
) : ReminderViewModel(
    durationModelFactory = durationModelFactory,
    frequencyModelFactory = frequencyModelFactory,
    notificationModelFactory =  notificationModelFactory
    ) {
    @AssistedInject.Factory
    interface Factory {
        fun create(task: Task): EditTaskViewModel
    }

    init{
        taskFields.taskDescription = task.description
    }



}

