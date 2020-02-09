package com.example.taskapp.viewmodels.addTask

import android.view.View
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskapp.database.entities.Task
import com.example.taskapp.repos.task.TaskRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddTaskViewModel @Inject constructor(
     val taskFields: TaskFields,
    private val taskRepository: TaskRepository
) : ViewModel() {

    val onFocusTaskName: View.OnFocusChangeListener = View.OnFocusChangeListener { view, focused ->
        val text = (view as EditText).text.toString()
        if (!focused && text.isNotEmpty()) {
            taskFields.isTaskNameValid(true)
        }
    }

    fun createTaskDetails(): TaskDetails {
        return TaskDetails(name = taskFields.taskName,description =  taskFields.taskDescription)
    }

     fun saveTask()  {
         viewModelScope.launch {
             taskRepository.saveTask(Task(name = taskFields.taskName,
                 description = taskFields.taskDescription))
         }
    }

    companion object


}

