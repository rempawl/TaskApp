package com.example.taskapp.database.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Frequency(
    val isDaily: Boolean ,
    val frequency:  Int
):Parcelable

@Parcelize
data class Duration(
    val isDate: Boolean,
    val duration: Long,
    val noDate : Boolean = false
):Parcelable

@Parcelize
data class NotificationTime( val hour : Int=0,val minute : Int=0, val isSet : Boolean) : Parcelable

@Parcelize
data class Reminder(
    @Embedded val frequency: Frequency,
    @Embedded val duration: Duration,
    @Embedded val  notificationTime: NotificationTime
) :Parcelable{
}