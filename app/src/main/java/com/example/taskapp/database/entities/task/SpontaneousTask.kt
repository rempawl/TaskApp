package com.example.taskapp.database.entities.task

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(tableName = "spontaneous tasks")
data class SpontaneousTask(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: LocalDate = LocalDate.now(),
    @ForeignKey(
        parentColumns = ["taskID"], childColumns = ["parentTaskID"], entity = DbTask::class,
        onDelete = ForeignKey.CASCADE
    ) val parentTaskID: Long
)