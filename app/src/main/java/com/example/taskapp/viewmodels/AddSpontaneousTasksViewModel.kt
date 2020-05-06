package com.example.taskapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.taskapp.adapters.TaskID
import com.example.taskapp.repos.task.TaskRepositoryInterface
import javax.inject.Inject

class AddSpontaneousTasksViewModel @Inject constructor(private val taskRepository: TaskRepositoryInterface) :
    ViewModel(){

    val onCheckedListener = ::onAddCheckboxChecked

    fun addSpontaneousTasks() {
//        task
    }

    private val _checkedTasksIds = mutableSetOf<TaskID>()
    val checkedTasksIds : List<TaskID>
        get() = _checkedTasksIds.toList()

    private fun onAddCheckboxChecked(isChecked: Boolean, id: TaskID) {
        if (isChecked) {
            _checkedTasksIds.add(id)
        } else {
            _checkedTasksIds.remove(id)
        }
    }

    val tasks = liveData {
        val data = taskRepository.getNotTodayTasks()
        emit(data)
    }
}
