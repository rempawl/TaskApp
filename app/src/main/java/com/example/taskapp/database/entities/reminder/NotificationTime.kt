package com.example.taskapp.database.entities.reminder

import android.os.Parcelable
import org.threeten.bp.LocalTime
import kotlinx.parcelize.Parcelize

@Parcelize
data class  NotificationTime(val hour: Int, val minute: Int, val isSet: Boolean = false) :
    Parcelable {
    fun convertToLocalTime() : LocalTime = LocalTime.of(hour, minute)

    companion object{
        fun from(time: LocalTime, isSet: Boolean = true) =
            NotificationTime(hour = time.hour, minute = time.minute,isSet = isSet)

    }

}