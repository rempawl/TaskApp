package com.example.taskapp.database.entities.task

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.taskapp.database.entities.reminder.Reminder
import kotlinx.android.parcel.Parcelize

fun DefaultTask.toTaskMinimal(): TaskMinimal {
    return TaskMinimal(
        taskID = this.taskID,
        name = this.name,
        description = this.description
    )
}

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
