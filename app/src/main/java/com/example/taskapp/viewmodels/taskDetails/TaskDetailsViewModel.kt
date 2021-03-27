package com.example.taskapp.viewmodels.taskDetails

import androidx.lifecycle.*
import com.example.taskapp.data.Result
import com.example.taskapp.data.reminder.Reminder
import com.example.taskapp.data.task.Task
import com.example.taskapp.dataSources.task.TaskRepository
import com.example.taskapp.utils.providers.DispatcherProvider
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import org.threeten.bp.format.DateTimeFormatter
import kotlin.coroutines.coroutineContext


class TaskDetailsViewModel @AssistedInject constructor(
    @Assisted private val taskID: Long,
    private val taskRepository: TaskRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {


    suspend fun deleteTask(): Int {
        return taskRepository.deleteByID(taskID)
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(taskID: Long): TaskDetailsViewModel
    }

    val result: LiveData<Result<*>> = liveData(dispatcherProvider.mainDispatcher) {
        val data = taskRepository.getTaskByID(taskID).asLiveData(coroutineContext)
        emitSource(data)
    }

    val reminder: LiveData<Reminder?> = Transformations.map(result) { res ->
        getReminder(res)
    }

    val taskToEdit: LiveData<Task?> = Transformations.map(result) { res ->
        checkIfCanEditTask(res)
    }

    val begDate: LiveData<String?> =
        Transformations.map(reminder) { reminder -> reminder?.begDate?.format(DATE_FORMATTER) }

    private fun getReminder(res: Result<*>): Reminder? {
        return res.takeIf { res.checkIfIsSuccessAnd<Task>() }?.let {
            it as Result.Success
            (it.data as Task).reminder
        }
    }

    private fun checkIfCanEditTask(result: Result<*>): Task? {
        return result.takeIf {
            it.isSuccess()
        }?.let {
            it.checkIfIsSuccessAnd<Task>()
            it as Result.Success
            it.data as Task
        }
    }


    companion object {
        val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    }
}

