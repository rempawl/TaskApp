package com.example.taskapp.data.task

import android.os.Parcelable
import com.example.taskapp.data.reminder.Reminder
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

    //todo refactor
    fun updateRealizationDate(): Task? {
        val reminder = this.reminder!!.updateRealizationDate()

        //if date has not been updated the reminder instance stays the same
        return if (this.reminder === reminder) {
            null
        } else {
            this.copy(reminder = reminder)
        }
    }

}
