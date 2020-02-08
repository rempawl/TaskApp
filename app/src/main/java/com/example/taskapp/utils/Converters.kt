package com.example.taskapp.utils

import androidx.room.TypeConverter
import com.example.taskapp.fragments.addReminder.DayOfWeekHash
import dagger.Reusable
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import javax.inject.Inject

@Reusable
class Converters @Inject constructor(){
    @TypeConverter
    fun localDateToLong(date: LocalDate): Long = date.toEpochDay()

    @TypeConverter
    fun longToLocalDate(date: Long): LocalDate = LocalDate.ofEpochDay(date)


        /**
         *  setting the i-th bit of result to 1 if dayOfWeek HashCode is  inside [daysOfWeek] set
         *
         */
        fun daysOfWeekToInt(daysOfWeek: Set<DayOfWeekHash>): Int {
            var result = 0
            val days = DayOfWeek.values()
            for (i in 0..days.lastIndex) {
                if (daysOfWeek.contains(days[i].hashCode())) {
                    result += 1.shl(i)
                }
            }
            return result
        }
//    if (result.ushr(i).and(1) == 1) {
//    }

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