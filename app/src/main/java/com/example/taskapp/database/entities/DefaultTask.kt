package com.example.taskapp.database.entities

import android.os.Parcelable
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDate

fun DefaultTask.toTaskMinimal(): TaskMinimal {
    return TaskMinimal(
        taskID = this.taskID,
        name = this.name,
        description = this.description
    )
}

@Entity(tableName = "spontaneous tasks")
data class SpontaneousTask(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: LocalDate = LocalDate.now(),
    @ForeignKey(
        parentColumns = ["taskID"], childColumns = ["parentTaskID"], entity = DefaultTask::class,
        onDelete = CASCADE
    ) val parentTaskID: Long
)

@Parcelize
data class TaskMinimal(val taskID: Long, val name: String, val description: String) : Parcelable

@Parcelize
@Entity(
    tableName = "tasks",
    indices = [Index(value = ["name"], unique = true)]
)
data class DefaultTask constructor(
    @PrimaryKey(autoGenerate = true) val taskID: Long = 0,
    val name: String,
    val description: String = "",
    @Embedded val reminder: Reminder? = null
) : Parcelable {

    /**
     * returns null if realization date didn't change
     */
    fun updateRealizationDate(): DefaultTask? {
        val reminder = this.reminder!!.updateRealizationDate()

        //if date has not been updated the reminder instance stays the same
        return if (this.reminder === reminder) {
            null
        } else {
            this.copy(reminder = reminder)
        }
    }

}
