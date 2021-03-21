package com.example.taskapp.database.entities.task

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class DbTaskMinimal(val taskID: Long, val name: String, val description: String)