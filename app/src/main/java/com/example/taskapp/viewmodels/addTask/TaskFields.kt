package com.example.taskapp.viewmodels.addTask

import android.view.View
import android.widget.EditText
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.example.taskapp.BR
import com.example.taskapp.R
import com.example.taskapp.database.entities.Task
import javax.inject.Inject

class TaskFields @Inject constructor() : BaseObservable() {


    val taskNameError: ObservableField<Int> = ObservableField()

    var taskName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    @Bindable
    var taskDescription: String = ""


    @Bindable
    fun isValid(): Boolean {
        return isTaskNameValid(false)
    }

    fun validateTaskDescription() {
        taskDescription = ""
        notifyPropertyChanged(BR.taskDescription)
    }


    fun createTaskDetails(): TaskDetails {
        return TaskDetails(taskName, taskDescription)
    }


    fun createTask(): Task {
        return Task(name = taskName, description = taskDescription)
    }


    fun isTaskNameValid(setMessage: Boolean): Boolean {
        return if (taskName.isNotBlank() && taskName.length >= MIN_LENGTH) {
            taskNameError.set(null)
            true
        } else {
            if (setMessage) {
                taskNameError.set(R.string.task_name_error)
            } else {
                taskNameError.set(null)
            }
            false
        }


    }

    companion object {
        const val MIN_LENGTH = 3
        @JvmStatic
        @BindingAdapter("setError")
        fun setError(view: EditText, stringOrRsrcID: Any?) {
            if (stringOrRsrcID != null) {
                when (stringOrRsrcID) {
                    is String -> view.error = stringOrRsrcID
                    is Int -> view.apply {
                        val text = context.resources.getString(stringOrRsrcID)
                        error = text
                    }
                }
            }

        }

        @JvmStatic
        @BindingAdapter("onFocus")
        fun bindFocusChange(
            editText: EditText,
            onFocusChangeListener: View.OnFocusChangeListener?
        ) {
            onFocusChangeListener ?: return
            editText.onFocusChangeListener = onFocusChangeListener

        }

    }
}