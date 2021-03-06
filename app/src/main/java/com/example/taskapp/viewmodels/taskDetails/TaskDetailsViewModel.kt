package com.example.taskapp.viewmodels.taskDetails

import androidx.lifecycle.*
import com.example.taskapp.data.Result
import com.example.taskapp.data.reminder.Reminder
import com.example.taskapp.data.task.Task
import com.example.taskapp.dataSources.task.TaskRepository
import com.example.taskapp.utils.DateUtils.DATE_FORMATTER
import com.example.taskapp.utils.providers.DispatcherProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlin.coroutines.coroutineContext


class TaskDetailsViewModel @AssistedInject constructor(
    @Assisted private val taskID: Long,
    private val taskRepository: TaskRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {


    @AssistedFactory
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

    suspend fun deleteTask(): Int {
        return taskRepository.deleteByID(taskID)
    }

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


    companion object
}

