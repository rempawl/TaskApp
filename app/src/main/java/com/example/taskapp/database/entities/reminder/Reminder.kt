package com.example.taskapp.database.entities.reminder

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.parcelize.Parcelize
import org.threeten.bp.LocalDate


@Parcelize
data class Reminder(
    val begDate: LocalDate,
    @Embedded val frequency: Frequency,
    @Embedded val duration: Duration,
    @Embedded val notificationTime: NotificationTime,
    val expirationDate : LocalDate,
    val realizationDate: LocalDate
) : Parcelable{

     //returns this if realization date has not been updated else new instance with updated realization date
     fun updateRealizationDate() : Reminder {
        val date = frequency.getUpdateDate(lastRealizationDate = realizationDate)
        return if(date != realizationDate){
            this.copy(realizationDate = date)
        }else{
            this
        }
    }


}