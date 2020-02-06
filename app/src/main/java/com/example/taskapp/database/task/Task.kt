package com.example.taskapp.database.task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(@PrimaryKey val taskID: Long =0,
                val name :String,
                val details: String = ""
                ) {
}