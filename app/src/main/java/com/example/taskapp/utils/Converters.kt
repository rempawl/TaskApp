package com.example.taskapp.utils

import com.example.taskapp.fragments.addReminder.DayOfWeekHash
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

object Converters {
    fun localDateToLong(date: LocalDate): Long = date.toEpochDay()

    fun longToLocalDate(date: Long): LocalDate = LocalDate.ofEpochDay(date)

    /**
     *  setting the i-th bit of result to 1 if dayOfWeek HashCode is  inside [daysOfWeek] set
     *
     */
    fun daysOfWeekToInt(daysOfWeek: Set<DayOfWeekHash>): Int {
        var result = 0
        val days = DayOfWeek.values()
        for (i in 0..days.lastIndex) {
            if (daysOfWeek.contains(days[i].hashCode() ) ) {
                    result += 1.shl(i)
                }
        }
        return result
    }
//    if (result.ushr(i).and(1) == 1) {
//    }

}