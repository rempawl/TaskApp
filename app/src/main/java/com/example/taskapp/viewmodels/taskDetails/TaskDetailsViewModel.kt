package com.example.taskapp.viewmodels.taskDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.taskapp.database.entities.reminder.Reminder
import com.example.taskapp.database.entities.task.DefaultTask
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.providers.DispatcherProvider
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import org.threeten.bp.format.DateTimeFormatter

class TaskDetailsViewModel @AssistedInject constructor(
    @Assisted private val taskID: Long,
    private val taskRepository: TaskRepositoryInterface,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {


    suspend fun deleteTask(): Int {
        return taskRepository.deleteByID(taskID)
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(taskID: Long): TaskDetailsViewModel
    }

    val task: LiveData<DefaultTask> = liveData(dispatcherProvider.provideIoDispatcher()) {
        val data = taskRepository.getTaskByID(taskID)
        emit(data)
    }

    val reminder: LiveData<Reminder?> = Transformations.map(task) { task -> task.reminder }

    val begDate: LiveData<String?> =
        Transformations.map(reminder) { reminder -> reminder?.begDate?.format(DATE_FORMATTER) }

    companion object {
        val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    }
}
