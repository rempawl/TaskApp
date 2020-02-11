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



    companion object{

        @Volatile private var INSTANCE  : Converters? = null

        fun getInstance() : Converters{
            if(INSTANCE != null){
                return INSTANCE as Converters
            }else{
                INSTANCE = Converters()
                return INSTANCE as Converters
            }
        }
    }

}