package com.example.taskapp.viewmodels.reminder

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.example.taskapp.BR
import com.example.taskapp.database.entities.Duration
import com.example.taskapp.utils.reminder.ReminderDurationState
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import org.threeten.bp.LocalDate

class DurationModel @AssistedInject constructor(@Assisted duration: Duration?) : BaseObservable() {

    @AssistedInject.Factory
    interface Factory{
        fun create(duration: Duration?  = null) : DurationModel
    }
    init{
        if(duration != null){
            when(val durState = duration.convertToDurationState()){
                   is ReminderDurationState.NoEndDate -> {setNoEndDateDurationState() }
                   is ReminderDurationState.DaysDuration -> setDaysDurationState(days = durState.days)
                   is ReminderDurationState.EndDate -> setEndDateDurationState(endDate = durState.date)
               }
        }
    }

    private var durationState: ReminderDurationState = ReminderDurationState.NoEndDate
        set(value) {
            field = value
            notifyPropertyChanged(BR.dateValid)
        }

    //true when error message should be displayed
    val begDateError = ObservableField<Boolean>(false)
    val endDateError = ObservableField<Boolean>(false)


    @Bindable
    var currentDaysDuration = 10
        private set(value) {
            field = value
            notifyPropertyChanged(BR.currentDaysDuration)
        }

    @Bindable
    var beginningDate =
        TODAY
        set(value) {
            if (isBeginningDateValid(value)) {
                field = value
                notifyPropertyChanged(BR.beginningDate)
                begDateError.set(false)
            } else {
                begDateError.set(true)
            }
        }


    @Bindable
    var currentEndDate: LocalDate = LocalDate.ofYearDay(TODAY.year, TODAY.dayOfYear + 10)
        private set(value) {
            if (isEndDateValid(value)) {
                field = value
                notifyPropertyChanged(BR.currentEndDate)
                endDateError.set(false)
            } else {
                endDateError.set(true)
            }

        }


    @Bindable
    fun isDateValid(): Boolean {
        return if (durationState is ReminderDurationState.EndDate) {
            isEndDateValid()
        } else {
            true
        }
    }

    private fun isEndDateValid(date: LocalDate = currentEndDate) =
        !date.isBefore(beginningDate)


    private fun isBeginningDateValid(date: LocalDate): Boolean {
        val isValid = (date.isEqual(TODAY) || date.isAfter(
            TODAY
        ))
        return if (durationState is ReminderDurationState.EndDate) {
            isValid && date.isBefore(currentEndDate)
        } else {
            isValid
        }
    }

    fun setNoEndDateDurationState() {
        durationState = ReminderDurationState.NoEndDate
    }

    fun setDaysDurationState(days: Int = currentDaysDuration) {
        durationState = ReminderDurationState.DaysDuration(days)
        currentDaysDuration = days
    }

    fun setEndDateDurationState(endDate: LocalDate = currentEndDate) {
        durationState = ReminderDurationState.EndDate(endDate)
        currentEndDate = endDate
    }

    fun getDuration(): Duration = durationState.convertToDuration()

    fun getExpirationDate() = durationState.calculateEndDate(beginningDate)

    companion object {
        val TODAY: LocalDate = LocalDate.now()
    }


}