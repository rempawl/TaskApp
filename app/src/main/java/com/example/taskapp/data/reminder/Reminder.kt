package com.example.taskapp.data.reminder

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
    val expirationDate: LocalDate,
    val realizationDate: LocalDate
) : Parcelable {

    fun checkIfRealizationDateShouldBeUpdated(): Boolean {
        return frequency.getUpdateDate(lastRealizationDate = realizationDate) != realizationDate
    }

    fun updateRealizationDate(): Reminder {
        val date = frequency.getUpdateDate(lastRealizationDate = realizationDate)
        return this.copy(realizationDate = date)
    }


}