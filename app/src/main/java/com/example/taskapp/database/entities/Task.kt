package com.example.taskapp.database.entities

import android.os.Parcelable
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDate

@Entity(tableName = "spontaneous tasks")
data class SpontaneousTask(
    @PrimaryKey(autoGenerate = true) val id : Long = 0,
    val date : LocalDate = LocalDate.now(),
    @ForeignKey(
        parentColumns = ["taskID"], childColumns = ["parentTaskID"], entity = Task::class,
        onDelete = CASCADE
    ) val parentTaskID: Long
)

@Entity(
    tableName = "tasks",
    indices = [Index(value = ["name"], unique = true)]
)
@Parcelize
data class Task constructor(
    @PrimaryKey(autoGenerate = true) val taskID: Long = 0,
    val name: String,
    val description: String = "",
    @Embedded val reminder: Reminder? = null
) : Parcelable {

    /**
     * returns null if realization date didn't change
     */
    fun updateRealizationDate(): Task? {
        val reminder = this.reminder!!.updateRealizationDate()

        //if date has not been updated the reminder instance stays the same
        return if (this.reminder === reminder) {
            null
        } else {
            this.copy(reminder = reminder)
        }
    }

}


@Parcelize
data class TaskMinimal(val taskID: Long, val name: String, val description: String) : Parcelable

