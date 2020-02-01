package com.example.taskapp.viewmodels.addTask

import android.view.View
import android.widget.EditText
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AddTaskViewModel @Inject constructor(val taskFields: TaskFields) : ViewModel() {

    val onFocusTaskName: View.OnFocusChangeListener = View.OnFocusChangeListener { view, focused ->
        val text = (view as EditText).text.toString()
        if (!focused && text.isNotEmpty()) {
            taskFields.isTaskNameValid(true)
        }
    }

    fun createTaskDetails(): TaskDetails {
        return TaskDetails(taskFields.taskName,taskFields.taskDescription)
    }

    companion object


}

