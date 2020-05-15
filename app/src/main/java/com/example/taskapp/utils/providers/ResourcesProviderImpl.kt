package com.example.taskapp.utils.providers

import android.content.Context
import com.example.taskapp.R
import dagger.Reusable
import javax.inject.Inject

@Reusable
class ResourcesProviderImpl @Inject constructor(private val context: Context)  : ResourcesProvider{
    override fun getDaysOfWeek(): Array<String> = context.resources.getStringArray(R.array.week_days_list)
}