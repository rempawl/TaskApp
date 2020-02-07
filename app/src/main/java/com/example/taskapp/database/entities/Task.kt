package com.example.taskapp.database.entities

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject


@Entity(tableName = "tasks",
    indices = [Index(value = ["name"],unique = true)]
)
@Parcelize
data class Task  constructor(
    @PrimaryKey(autoGenerate = true) val taskID: Long = 0,
    val name: String,
    val description: String = "",
  @Embedded  val reminder: Reminder? = null

) : Parcelable {
}

