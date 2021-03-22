package com.example.taskapp.viewmodels

import com.example.taskapp.data.task.Task
import com.example.taskapp.dataSources.task.TaskRepository
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import com.example.taskapp.viewmodels.reminder.durationModel.AddTaskDurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.NotificationModel
import com.example.taskapp.viewmodels.taskDetails.TaskDetailsModel
import javax.inject.Inject


class AddTaskViewModel @Inject constructor(
    taskDetailsModel: TaskDetailsModel,
    private val taskRepository: TaskRepository,
    durationModel: AddTaskDurationModel,
    notificationModel: NotificationModel,
    frequencyModel: FrequencyModel
) : ReminderViewModel(
    taskDetailsModel = taskDetailsModel,
    task = Task(name = ""),
    durationModel = durationModel,
    notificationModel = notificationModel,
    frequencyModel = frequencyModel
) {


    override suspend fun addTask(task: Task) {
        taskRepository.saveTask(task)
    }


}


