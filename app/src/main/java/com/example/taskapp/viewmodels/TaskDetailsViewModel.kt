package com.example.taskapp.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.taskapp.MainActivity
import com.example.taskapp.database.entities.Task
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.repos.task.TaskRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TaskDetailsViewModel @AssistedInject constructor(
    @Assisted val taskMinimal: TaskMinimal,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val isTaskDeleted  = MutableLiveData<Boolean>(false)
    fun getTaskDeleted() : LiveData<Boolean> = isTaskDeleted

    //todo editBtn
    fun deleteTask() {
        viewModelScope.launch {
            Log.d(MainActivity.TAG, taskRepository.deleteByID(taskMinimal.taskID).toString())
        }
        isTaskDeleted.value = true
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(taskMinimal: TaskMinimal): TaskDetailsViewModel
    }

    val task: LiveData<Task> = liveData {
        val data = taskRepository.getTaskByID(taskMinimal.taskID) ?: Task(name="error")
        emit(data)
    }


}
