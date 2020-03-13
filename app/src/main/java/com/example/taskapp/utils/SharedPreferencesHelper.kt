package com.example.taskapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.taskapp.MyApp
import dagger.Reusable
import org.threeten.bp.LocalDate
import javax.inject.Inject

@Reusable
class SharedPreferencesHelper @Inject constructor(private val context: Context) {
     private val sharedPreferences: SharedPreferences by lazy{
        context.getSharedPreferences(MyApp.PREFERENCES_NAME,Context.MODE_PRIVATE)
    }

    fun updateCurrentDate(date: LocalDate){
        sharedPreferences.edit().putLong(CURRENT_DATE_KEY,date.toEpochDay()).apply()
    }

    fun setErrorCurrentDate(){
        sharedPreferences.edit().putLong(CURRENT_DATE_KEY,-1).apply()
    }

    fun getCurrentDate(): Long = sharedPreferences.getLong(CURRENT_DATE_KEY,-1)

    companion object{
        const val  CURRENT_DATE_KEY = "current updated date"
    }

}