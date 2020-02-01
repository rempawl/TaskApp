package com.example.taskapp.viewmodels.addTask

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.example.taskapp.BR
import com.example.taskapp.MainActivity
import com.example.taskapp.R
import javax.inject.Inject

class TaskFields @Inject constructor() : BaseObservable() {


    val taskNameError: ObservableField<Int> = ObservableField()

    var taskName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    var taskDescription: String = ""

    @Bindable
    fun isValid(): Boolean{
        return isTaskNameValid(false)
    }

    fun isTaskNameValid(setMessage: Boolean): Boolean {
        return if (taskName.isNotBlank() && taskName.length >= MIN_LENGTH) {
            taskNameError.set(null)
            true
        } else  {
            if (setMessage) {
                taskNameError.set(R.string.task_name_error)
            }else{
                taskNameError.set(null)
            }
            false
        }


    }

    companion object {
        const val MIN_LENGTH = 3

    }
}