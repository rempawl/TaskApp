package com.example.taskapp.database.entities.reminderEntities

import android.os.Parcelable
import com.example.taskapp.viewmodels.reminder.ReminderDurationState
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDate

@Parcelize
data class Duration(
    val durState: Int,
    val duration: Long= 0 //when durState is NoEndDate then duration is 0
) : Parcelable {
    fun convertToDurationState(): ReminderDurationState {
        return when (durState) {
            ReminderDurationState.DAYS_DURATION_INDEX -> ReminderDurationState.DaysDuration(
                duration.toInt()
            )
            ReminderDurationState.END_DATE_DURATION_INDEX -> {
                ReminderDurationState.EndDate(
                    LocalDate.ofEpochDay(duration)
                )
            }
            ReminderDurationState.NO_END_DATE_DURATION_INDEX -> ReminderDurationState.NoEndDate
            else -> throw IndexOutOfBoundsException()
        }
    }
}