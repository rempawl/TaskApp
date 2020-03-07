package com.example.taskapp.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(tableName = "streaks")
data class Streak(@PrimaryKey(autoGenerate = true) val streakID : Long =0,
                  @ForeignKey(entity = Task::class,onDelete = CASCADE,childColumns = ["parentTaskID"],parentColumns = ["taskID"]) val parentTaskID : Long,
                  var isCurrent : Boolean = true,
                  val duration: Int,
                  val begDate : LocalDate,
                  var endDate : LocalDate
)