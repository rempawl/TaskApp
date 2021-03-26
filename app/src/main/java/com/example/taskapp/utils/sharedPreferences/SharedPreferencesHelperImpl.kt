package com.example.taskapp.utils.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.taskapp.MyApp
import org.threeten.bp.LocalDate
import javax.inject.Inject


class SharedPreferencesHelperImpl @Inject constructor(private val context: Context) :
    SharedPreferencesHelper {

    private fun getSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(MyApp.PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    override fun updateCurrentDate(date: LocalDate) {
        getSharedPreferences().edit { putLong(CURRENT_DATE_KEY, date.toEpochDay()) }
    }

    override fun getCurrentDate(): Long = getSharedPreferences()
        .getLong(CURRENT_DATE_KEY, -1)

    companion object {

        private const val CURRENT_DATE_KEY = "current updated date"
    }
}