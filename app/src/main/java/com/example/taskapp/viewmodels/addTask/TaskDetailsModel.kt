package com.example.taskapp.viewmodels.addTask

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.example.taskapp.BR
import com.example.taskapp.R
import com.example.taskapp.database.entities.DefaultTask
import javax.inject.Inject

abstract class TaskDetailsModel : BaseObservable() {

    abstract val taskNameError: ObservableField<Int>

    abstract var taskName: String

    abstract var taskDescription: String

    @Bindable
    abstract fun isValid(): Boolean

    abstract fun validateTaskDescription()

    abstract fun createTask(): DefaultTask

    abstract fun isTaskNameValid(setMessage: Boolean): Boolean

    companion object {
        const val MIN_LENGTH = 3
    }
}

class DefaultTaskDetailsModel @Inject constructor() : TaskDetailsModel() {


    override val taskNameError: ObservableField<Int> = ObservableField()

    @Bindable
    override fun isValid(): Boolean {
        return isTaskNameValid(false)
    }

    @Bindable
    override var taskDescription: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.taskDescription)
        }

    override var taskName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    override fun validateTaskDescription() {
        taskDescription = ""
    }

    override fun createTask(): DefaultTask {
        return DefaultTask(name = taskName, description = taskDescription)
    }

    override fun isTaskNameValid(setMessage: Boolean): Boolean {
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



}