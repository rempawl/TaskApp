package com.example.taskapp.viewmodels.reminder.durationModel

import androidx.databinding.library.baseAdapters.BR
import com.example.taskapp.MyApp
import com.example.taskapp.database.entities.Duration
import com.example.taskapp.viewmodels.reminder.ReminderDurationState
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import org.threeten.bp.LocalDate

class  EditTaskDurationModel @AssistedInject constructor(
    @Assisted duration: Duration?,
    @Assisted begDate: LocalDate
) :
    DurationModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(
            duration: Duration? = null,
            begDate: LocalDate = LocalDate.now()
        ): EditTaskDurationModel
    }


    override var durationState: ReminderDurationState = ReminderDurationState.NoEndDate
        private set(value) {
            field = value
            notifyPropertyChanged(BR.datesValid)
        }


    init {
        if(!begDate.isEqual(MyApp.TODAY) ){
            beginningDate = begDate
        }

        if (duration != null) {
            when (val durState = duration.convertToDurationState()) {
                is ReminderDurationState.NoEndDate -> {
                    setNoEndDateDurationState()
                }
                is ReminderDurationState.DaysDuration -> setDaysDurationState(days = durState.days)
                is ReminderDurationState.EndDate -> setEndDateDurationState(endDate = durState.date)
            }

        }
    }




    override fun setNoEndDateDurationState() {
        durationState = ReminderDurationState.NoEndDate
    }

    override fun setDaysDurationState(days: Int) {
        durationState = ReminderDurationState.DaysDuration(days)
        currentDaysDuration = days
    }

    override fun setEndDateDurationState(endDate: LocalDate) {
        durationState = ReminderDurationState.EndDate(endDate)
        currentEndDate = endDate
    }

    override fun isBeginningDateValid(date: LocalDate): Boolean {

        return if (durationState is ReminderDurationState.EndDate) {
             date.isBefore(currentEndDate)
        } else {
            true
        }
    }


}
