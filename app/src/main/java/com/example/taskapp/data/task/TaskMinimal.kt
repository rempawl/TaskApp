package com.example.taskapp.data.task

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class TaskMinimal(
    val taskID: Long = 0,
    val name: String,
    val description: String = "",
) : Parcelable