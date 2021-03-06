package com.example.taskapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.taskapp.adapters.TaskID
import com.example.taskapp.data.Result
import com.example.taskapp.dataSources.task.TaskRepository
import com.example.taskapp.utils.providers.DispatcherProvider
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class AddSpontaneousTasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val dispatcherProvider: DispatcherProvider
) :
    ViewModel() {

    val onCheckedListener = ::onAddCheckboxChecked

    val result: LiveData<Result<*>> = liveData(dispatcherProvider.mainDispatcher) {
        val data = taskRepository.getNotTodayTasks().asLiveData(coroutineContext)
        emitSource(data)
    }


    private val _checkedTasksIds = mutableSetOf<TaskID>()
    val checkedTasksIds: List<TaskID>
        get() = _checkedTasksIds.toList()

    fun addSpontaneousTasks() {
//        task
    }

    private fun onAddCheckboxChecked(isChecked: Boolean, id: TaskID) {
        if (isChecked) {
            _checkedTasksIds.add(id)
        } else {
            _checkedTasksIds.remove(id)
        }
    }

}
