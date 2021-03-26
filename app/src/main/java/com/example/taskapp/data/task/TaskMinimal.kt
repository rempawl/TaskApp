package com.example.taskapp.data.task

import android.content.Intent
import android.os.Parcelable
import com.example.taskapp.database.entities.task.DbTaskMinimal
import kotlinx.parcelize.Parcelize


@Parcelize
data class TaskMinimal(
    val taskID: Long = 0,
    val name: String,
    val description: String = "",
) : Parcelable {
    companion object {
        fun from(task: DbTaskMinimal) = TaskMinimal(
            taskID = task.taskID, task.name, description = task.description
        )

        fun from(intent: Intent): TaskMinimal {

            val name = intent.getStringExtra(TASK_NAME_KEY) ?: "error"
            val desc = intent.getStringExtra(TASK_DESC_KEY) ?: "error"
            val id = intent.getLongExtra(TASK_ID_KEY, -1)

            return TaskMinimal(id, name, desc)

        }

        const val TASK_NAME_KEY = "task name"
        const val TASK_DESC_KEY = "task desc"
        const val TASK_ID_KEY = "task id"

    }

}