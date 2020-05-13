package com.example.taskapp.database.entities.reminder

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalTime

@Parcelize
data class  NotificationTime(val hour: Int, val minute: Int, val isSet: Boolean = false) :
    Parcelable {
    fun convertToLocalTime() : LocalTime =
        LocalTime.of(hour, minute)

    companion object{
        fun from(time: LocalTime, isSet: Boolean = true) =
            NotificationTime(
                time.hour,
                time.minute,
                isSet
            )

    }

}