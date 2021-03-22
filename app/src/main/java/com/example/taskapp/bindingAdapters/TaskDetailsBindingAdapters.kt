package com.example.taskapp.bindingAdapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.taskapp.R
import com.example.taskapp.data.Result
import com.example.taskapp.data.task.Task
import com.example.taskapp.data.task.TaskMinimal

object TaskDetailsBindingAdapters {

    @JvmStatic
    @BindingAdapter("bindDescription")
    fun TextView.bindDescription(result: Result<*>?) {
        result ?: return
        val task = checkIfResultIsSuccessAndTaskMinimal(result) ?: return
        text = context.getString(R.string.task_description, task.description)
        visibility = if (task.description.isBlank()) View.GONE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("bindName")
    fun TextView.bindTaskName(result: Result<*>?) {
        result ?: return
        val task = checkIfResultIsSuccessAndTaskMinimal(result) ?: return
        text = context.getString(R.string.task_name, task.name)
    }

    private fun checkIfResultIsSuccessAndTaskMinimal(result: Result<*>): TaskMinimal? {
        return result.takeIf { it.isSuccess() }?.let { res ->
            res as Result.Success<*>
            val data = res.data
            if( data is Task) return data.toTaskMinimal()
            check(res.data is TaskMinimal ) { "data should be task " }
            res.data as TaskMinimal
        }
    }
}