package com.example.taskapp.viewmodels

import androidx.lifecycle.*
import com.example.taskapp.database.entities.Task
import com.example.taskapp.repos.task.TaskRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class TaskDetailsViewModel @AssistedInject constructor(
    @Assisted val taskID: Long,
    private val taskRepository: TaskRepository
) : ViewModel() {


    private val isTaskDeleted  = MutableLiveData<Boolean>(false)
    fun getTaskDeleted() : LiveData<Boolean> = isTaskDeleted

    //todo editBtn
    fun deleteTask() {
        viewModelScope.launch {
            taskRepository.deleteByID(taskID).toString()
        }
        isTaskDeleted.value = true
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(taskID: Long): TaskDetailsViewModel
    }

    val task: LiveData<Task> = liveData {
        val data = taskRepository.getTaskByID(taskID)
        emit(data)
    }


}
