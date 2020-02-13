package com.example.taskapp.database.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "tasks",
    indices = [Index(value = ["name"],unique = true)]
)
@Parcelize
data class Task  constructor(
    @PrimaryKey(autoGenerate = true) val taskID: Long = 0,
    val name: String,
    val description: String = "",
    @Embedded  val reminder: Reminder? = null
) : Parcelable


@Parcelize
data class TaskMinimal(val taskID: Long,val name: String,val description: String) : Parcelable

