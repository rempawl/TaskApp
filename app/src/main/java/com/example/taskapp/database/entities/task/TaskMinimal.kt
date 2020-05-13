package com.example.taskapp.database.entities.task

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaskMinimal(val taskID: Long, val name: String, val description: String) : Parcelable