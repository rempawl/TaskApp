package com.example.taskapp.workers

import com.example.taskapp.MyApp
import com.example.taskapp.data.Result
import com.example.taskapp.data.task.Task
import com.example.taskapp.dataSources.task.TaskRepository
import com.example.taskapp.utils.alarmCreator.AlarmCreator
import kotlinx.coroutines.flow.first
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import javax.inject.Inject

class TaskUpdaterImpl @Inject constructor(
    private val repository: TaskRepository,
    private val alarmCreator: AlarmCreator
) : TaskUpdater() {
    private fun datePredicate(date: LocalDate, task: Task): Boolean =
        task.reminder!!.realizationDate == date
                && task.reminder.notificationTime.isSet


    @Suppress("UNCHECKED_CAST")
    override suspend fun filterAndUpdateTasks(): Result<*> {
        return try {
            val allTasks = repository.getTasks()
                .first { res -> res.checkIfIsSuccessAndListOf<Task>() } as List<Task>

            if (allTasks.isEmpty()) return Result.Success(allTasks)

            val tasks = allTasks.filter { task -> task.reminder != null }

            val updatedTasks = updateTaskList(tasks)
            setTodayNotifications(updatedTasks)

            Result.Success(updatedTasks)
        } catch (e: NoSuchElementException) {
            Result.Error(e)
        }

    }


    private suspend fun updateTaskList(tasks: List<Task>): List<Task> {
        val partitionedTasks = tasks
            .partition { task -> task.updateRealizationDate() != null }

        repository.updateTasks(partitionedTasks.first)

        return partitionedTasks.toList().flatten()
    }

    private fun setTodayNotifications(tasks: List<Task>) {
        tasks
            .filter { task ->
                datePredicate(MyApp.TODAY, task)
                        && task.reminder!!.notificationTime.convertToLocalTime()
                    .isAfter(LocalTime.now())
            }.forEach { task ->
                alarmCreator.setTaskNotificationAlarm(task)
            }
    }

}