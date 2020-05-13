package com.example.taskapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.taskapp.database.entities.task.TaskMinimal
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.dispatcherProvider.DispatcherProvider
import javax.inject.Inject

class TodayViewModel @Inject constructor(
    private val taskRepository: TaskRepositoryInterface,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

     val tasks: LiveData<List<TaskMinimal>> =
        liveData(dispatcherProvider.provideIoDispatcher()) {
            emitSource(taskRepository.getTodayMinTasks())
        }


}

