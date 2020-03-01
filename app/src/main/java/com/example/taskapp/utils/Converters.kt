package com.example.taskapp.utils

import androidx.room.TypeConverter
import dagger.Reusable
import org.threeten.bp.LocalDate
import javax.inject.Inject

@Reusable
class Converters @Inject constructor(){
    @TypeConverter
    fun localDateToLong(date: LocalDate): Long = date.toEpochDay()

    @TypeConverter
    fun longToLocalDate(date: Long): LocalDate = LocalDate.ofEpochDay(date)



    companion object

}