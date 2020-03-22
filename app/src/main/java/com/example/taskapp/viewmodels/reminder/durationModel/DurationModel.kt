package com.example.taskapp.viewmodels.reminder.durationModel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.library.baseAdapters.BR
import com.example.taskapp.database.entities.Duration
import com.example.taskapp.utils.reminder.ReminderDurationState
import org.threeten.bp.LocalDate

abstract class DurationModel(duration: Duration?,begDate: LocalDate): BaseObservable(){


    //Today for new tasks begDate for edited tasks
    private val validationDate: LocalDate = begDate

     var durationState: ReminderDurationState = ReminderDurationState.NoEndDate
        private set(value) {
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
    var beginningDate = validationDate
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
    init{
        if(duration != null){
            when(val durState = duration.convertToDurationState()){
                is ReminderDurationState.NoEndDate -> {setNoEndDateDurationState() }
                is ReminderDurationState.DaysDuration -> setDaysDurationState(days = durState.days)
                is ReminderDurationState.EndDate -> setEndDateDurationState(endDate = durState.date)
            }

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
        !date.isBefore(beginningDate) && !date.isBefore(LocalDate.now())


    private fun isBeginningDateValid(date: LocalDate): Boolean {
        val isValid = !date.isBefore(validationDate)
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

    companion object{
        private val TODAY = LocalDate.now()
    }




}