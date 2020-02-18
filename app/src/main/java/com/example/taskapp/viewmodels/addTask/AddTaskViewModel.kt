package com.example.taskapp.viewmodels.addTask

import android.view.View
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskapp.repos.task.TaskRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddTaskViewModel @Inject constructor(
    val taskFields: TaskDetailsModel,
    private val taskRepository: TaskRepository
) : ViewModel() {

    val onFocusTaskName: View.OnFocusChangeListener = View.OnFocusChangeListener { view, focused ->
        val text = (view as EditText).text.toString()
        if (!focused && text.isNotEmpty()) {
            taskFields.isTaskNameValid(true)
        }
    }


    val onFocusTaskDescription = View.OnFocusChangeListener { view, focused ->
        val text = (view as EditText).text.toString()
        if (!focused && text.isBlank()) {
            taskFields.validateTaskDescription()
        }
    }

    fun getTaskDetails(): TaskDetails {
        return taskFields.createTaskDetails()
    }

    fun saveTask() {
        viewModelScope.launch {
            taskRepository.saveTask(taskFields.createTask())
        }
    }

    companion object


}

