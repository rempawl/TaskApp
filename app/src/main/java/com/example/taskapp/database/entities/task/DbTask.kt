package com.example.taskapp.database.entities.task

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.taskapp.data.reminder.Reminder
import com.example.taskapp.data.task.Task

@Entity(
    tableName = "tasks",
    indices = [Index(value = ["name"], unique = true)]
)
data class DbTask constructor(
    @PrimaryKey(autoGenerate = true) val taskID: Long = 0,
    val name: String,
    val description: String = "",
    @Embedded val reminder: Reminder? = null
) {
    fun toTaskMinimal(): DbTaskMinimal {
        return DbTaskMinimal(
            taskID = this.taskID,
            name = this.name,
            description = this.description
        )
    }

    companion object {
        fun from(task: Task) = DbTask(
            taskID = task.taskID, task.name, description = task.description,
            reminder = task.reminder
        )
    }
}

