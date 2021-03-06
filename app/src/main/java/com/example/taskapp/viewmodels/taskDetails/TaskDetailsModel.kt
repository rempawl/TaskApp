package com.example.taskapp.viewmodels.taskDetails

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField

abstract class TaskDetailsModel : BaseObservable() {

    abstract val taskNameError: ObservableField<Int>

    abstract var taskName: String

    abstract var taskDescription: String

    @Bindable
    abstract fun isValid(): Boolean

    abstract fun isTaskNameValid(setMessage: Boolean): Boolean

    companion object {
        const val MIN_LENGTH = 3
    }
}

