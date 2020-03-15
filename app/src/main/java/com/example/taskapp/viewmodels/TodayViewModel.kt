package com.example.taskapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.taskapp.repos.task.TaskRepository
import org.threeten.bp.LocalDate
import javax.inject.Inject

class TodayViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {

    companion object {
        val TODAY: LocalDate = LocalDate.now()
    }

    val tasks = liveData {
        val data = taskRepository.getTodayMinTasks()
        emit(data)
    }



}
