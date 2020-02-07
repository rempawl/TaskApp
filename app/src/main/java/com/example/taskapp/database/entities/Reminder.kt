package com.example.taskapp.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


data class Frequency(
    val isDaily: Boolean ,
    val value: Int
)


data class Duration(
    val isDate: Boolean,
    val value: Long,
    val noDate : Boolean = false
)


@Entity(tableName = "reminder")
data class Reminder(
    @PrimaryKey(autoGenerate = true) val reminderID: Long = 0,
    val taskID: Long? = null,
    @Embedded val frequency: Frequency,
    @Embedded val duration: Duration

) {
}