package com.example.taskapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.taskapp.adapters.TaskID
import com.example.taskapp.data.Result
import com.example.taskapp.repos.task.TaskRepository
import com.example.taskapp.utils.providers.DispatcherProvider
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class AddSpontaneousTasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val dispatcherProvider: DispatcherProvider
) :
    ViewModel() {

    val onCheckedListener = ::onAddCheckboxChecked

    fun addSpontaneousTasks() {
//        task
    }

    private val _checkedTasksIds = mutableSetOf<TaskID>()
    val checkedTasksIds: List<TaskID>
        get() = _checkedTasksIds.toList()

    private fun onAddCheckboxChecked(isChecked: Boolean, id: TaskID) {
        if (isChecked) {
            _checkedTasksIds.add(id)
        } else {
            _checkedTasksIds.remove(id)
        }
    }

    val result: LiveData<Result<*>> = liveData(dispatcherProvider.ioDispatcher) {
        val data = taskRepository.getNotTodayTasks().asLiveData(coroutineContext)
        emitSource(data)
    }
}
