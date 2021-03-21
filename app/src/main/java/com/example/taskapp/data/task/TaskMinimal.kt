package com.example.taskapp.data.task

import android.os.Parcelable
import com.example.taskapp.database.entities.task.DbTaskMinimal
import kotlinx.parcelize.Parcelize

@Parcelize
class TaskMinimal(
    val taskID: Long = 0,
    val name: String,
    val description: String = "",
) : Parcelable {
    companion object {
        fun from(task: DbTaskMinimal) = TaskMinimal(
            taskID = task.taskID, task.name, description = task.description
        )
    }

}