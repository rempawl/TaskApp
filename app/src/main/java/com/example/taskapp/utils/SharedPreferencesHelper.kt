package com.example.taskapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.taskapp.MyApp
import org.threeten.bp.LocalDate


object SharedPreferencesHelper {

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(MyApp.PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun updateCurrentDate(date: LocalDate, context: Context) {
        getSharedPreferences(context).edit { putLong(CURRENT_DATE_KEY, date.toEpochDay()) }
    }

    fun setErrorCurrentDate(context: Context) {
        getSharedPreferences(context).edit { putLong(CURRENT_DATE_KEY, -1) }
    }

    fun getCurrentDate(context: Context): Long = getSharedPreferences(context)
        .getLong(CURRENT_DATE_KEY, -1)

    const val CURRENT_DATE_KEY = "current updated date"

}