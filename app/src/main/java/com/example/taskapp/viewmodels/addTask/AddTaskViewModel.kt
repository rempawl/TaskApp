package com.example.taskapp.viewmodels.addTask

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.taskapp.database.entities.DefaultTask

abstract class AddTaskViewModel : ViewModel(){
    abstract val onFocusTaskName: View.OnFocusChangeListener
    abstract val onFocusTaskDescription : View.OnFocusChangeListener

    abstract fun getTask(): DefaultTask
    abstract suspend fun saveTask()


}
