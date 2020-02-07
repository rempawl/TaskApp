package com.example.taskapp.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject


@Entity(tableName = "tasks")
data class Task @Inject constructor(@PrimaryKey(autoGenerate = true) val taskID: Long =0,
                val name :String,
                val description: String = "",
                @Embedded val reminder: Reminder? = null

                ) {
}