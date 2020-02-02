package com.example.taskapp.fragments.addReminder

import android.os.Parcelable
import androidx.databinding.BaseObservable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDate


typealias DayOfWeekHash = Int

sealed class ReminderFrequencyState {
    companion object {
        const val INITIAL_FREQUENCY = 1
    }
    data class Daily(val frequency: Int = INITIAL_FREQUENCY) : ReminderFrequencyState()

    data class WeekDays(val days: Set<DayOfWeekHash> = emptySet()) : ReminderFrequencyState()
}


sealed class ReminderDurationState   {

    object NoEndDate : ReminderDurationState()

    data class EndDate(val date: LocalDate) : ReminderDurationState()

    data class DaysDuration(val days: Int) : ReminderDurationState()
}

@Parcelize
enum class DetailsType : Parcelable {
    MEDICATION,
    TARGET,
    MEASUREMENT,
}

