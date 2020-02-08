package com.example.taskapp.database.entities

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDate

@Parcelize
data class Frequency(
    val isDaily: Boolean,
    val frequency: Int
) : Parcelable

@Parcelize
data class Duration(
    val isDate: Boolean,
    val duration: Long,
    val noDate: Boolean = false
) : Parcelable

@Parcelize
data class NotificationTime(val hour: Int, val minute: Int, val isSet: Boolean = false) : Parcelable

@Parcelize
data class Reminder(
    val begDate: LocalDate,
    @Embedded val frequency: Frequency,
    @Embedded val duration: Duration,
    @Embedded val notificationTime: NotificationTime
) : Parcelable {
}