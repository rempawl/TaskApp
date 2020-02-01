package com.example.taskapp.viewmodels.addTask

import android.content.ClipDescription
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaskDetails(val name: String,
                       val description: String = "") :Parcelable