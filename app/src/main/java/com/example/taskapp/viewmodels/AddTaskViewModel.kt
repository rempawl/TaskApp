package com.example.taskapp.viewmodels

import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.scheduler.SchedulerProvider
import com.example.taskapp.viewmodels.addTask.TaskDetailsModel
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import com.example.taskapp.viewmodels.reminder.durationModel.DurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.NotificationModel
import io.reactivex.Single
import javax.inject.Inject


class AddTaskViewModel @Inject constructor(
    taskDetailsModel: TaskDetailsModel,    
    private val taskRepository: TaskRepositoryInterface,
    durationModel: DurationModel,
    notificationModel: NotificationModel,
    frequencyModel: FrequencyModel,
    schedulerProvider: SchedulerProvider
) : ReminderViewModel(
    taskDetailsModel = taskDetailsModel,
    task = DefaultTask(name = ""),
    durationModel = durationModel,
    notificationModel = notificationModel,
    frequencyModel = frequencyModel,
    schedulerProvider = schedulerProvider
) {



    override suspend fun addTask(task: DefaultTask): Single<Long> {
        return taskRepository.saveTask(task)
    }



}


