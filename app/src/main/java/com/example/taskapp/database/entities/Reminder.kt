package com.example.taskapp.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


data class Frequency(
    val isDaily: Boolean ,
    val frequency:  Int
)

data class Duration(
    val isDate: Boolean,
    val duration: Long,
    val noDate : Boolean = false
)


data class Reminder(
    @Embedded val frequency: Frequency,
    @Embedded val duration: Duration
) {
}