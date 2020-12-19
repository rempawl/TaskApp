package com.example.taskapp.viewmodels

import com.example.taskapp.database.entities.task.DefaultTask
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import com.example.taskapp.viewmodels.reminder.durationModel.AddTaskDurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.NotificationModel
import com.example.taskapp.viewmodels.taskDetails.TaskDetailsModel
import javax.inject.Inject


class AddTaskViewModel @Inject constructor(
    taskDetailsModel: TaskDetailsModel,
    private val taskRepository: TaskRepositoryInterface,
    durationModel: AddTaskDurationModel,
    notificationModel: NotificationModel,
    frequencyModel: FrequencyModel
) : ReminderViewModel(
    taskDetailsModel = taskDetailsModel,
    task = DefaultTask(name = ""),
    durationModel = durationModel,
    notificationModel = notificationModel,
    frequencyModel = frequencyModel
) {


    override suspend fun addTask(task: DefaultTask) {
        taskRepository.saveTask(task)
    }


}


