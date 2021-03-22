package com.example.taskapp.bindingAdapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.taskapp.R
import com.example.taskapp.data.Result
import com.example.taskapp.data.task.Task
import com.example.taskapp.data.task.TaskMinimal

object TaskDetailsBindingAdapters {
    @BindingAdapter("bindDescription")
    fun TextView.bindDescription(result: Result<*>) {
        val task = checkIfResultIsSuccessAndTaskMinimal(result) ?: return
        text = context.getString(R.string.task_description, task.description)
        visibility = if (task.description.isBlank()) View.GONE else View.VISIBLE
    }

    @BindingAdapter("bindName")
    fun TextView.bindTaskName(result: Result<*>) {
        val task = checkIfResultIsSuccessAndTaskMinimal(result) ?: return
        text = context.getString(R.string.task_name, task.name)
    }

    private fun checkIfResultIsSuccessAndTaskMinimal(result: Result<*>): TaskMinimal? {
        return result.takeIf { it.isSuccess() }?.let {
            it as Result.Success<*>
            check(it.data is TaskMinimal || it.data is Task) { "data should be task " }
            it.data as TaskMinimal
        }
    }
}