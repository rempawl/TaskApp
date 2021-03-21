package com.example.taskapp.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.taskapp.database.entities.task.DbTask
import org.threeten.bp.LocalDate

@Entity(tableName = "streaks")
data class Streak(
    @PrimaryKey(autoGenerate = true) val streakID: Long = 0,
    @ForeignKey(
        entity = DbTask::class,
        onDelete = CASCADE,
        childColumns = ["parentTaskID"],
        parentColumns = ["taskID"]
    ) val parentTaskID: Long,
    val isCurrent: Boolean = true,
    val duration: Int,
    val begDate: LocalDate,
    val endDate: LocalDate
)