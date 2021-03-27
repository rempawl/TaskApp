package com.example.taskapp.data.task

import android.os.Parcelable
import com.example.taskapp.data.reminder.Reminder
import com.example.taskapp.database.entities.task.DbTask
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val taskID: Long = 0,
    val name: String,
    val description: String = "",
    val reminder: Reminder? = null
) : Parcelable {
    fun toTaskMinimal(): TaskMinimal {
        return TaskMinimal(
            taskID = this.taskID,
            name = this.name,
            description = this.description
        )
    }

    fun checkIfRealizationDateShouldBeUpdated() =
        reminder?.checkIfRealizationDateShouldBeUpdated() ?: false

    fun updateRealizationDate(): Task {
        val reminder = this.reminder?.updateRealizationDate()
        return this.copy(reminder = reminder)
    }

    companion object {
        fun from(task: DbTask) = Task(
            taskID = task.taskID, task.name, description = task.description,
            reminder = task.reminder
        )
    }


}
