package com.example.taskapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(@PrimaryKey(autoGenerate = true) val taskID: Long =0,
                val name :String,
                val description: String = ""
                ) {
}