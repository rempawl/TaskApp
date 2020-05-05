package com.example.taskapp.viewmodels.addTask

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.example.taskapp.BR
import com.example.taskapp.R
import javax.inject.Inject

class DefaultTaskDetailsModel @Inject constructor() : TaskDetailsModel() {

    override val taskNameError: ObservableField<Int> = ObservableField()

    @Bindable
    override fun isValid(): Boolean {
        return isTaskNameValid(false)
    }

    override var taskDescription: String = ""

    override var taskName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
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