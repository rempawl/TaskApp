package com.example.taskapp.utils.sharedPreferences

import org.threeten.bp.LocalDate

interface SharedPreferencesHelper {
    fun updateCurrentDate(date: LocalDate)
    fun getCurrentDate(): Long

}