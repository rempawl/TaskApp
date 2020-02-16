package com.example.taskapp.utils

import android.content.Context
import com.example.taskapp.R
import dagger.Reusable
import javax.inject.Inject

@Reusable
class ResourcesProvider @Inject constructor(private val context: Context) {
    fun getDaysOfWeek(): Array<String> = context.resources.getStringArray(R.array.week_days_list)
}