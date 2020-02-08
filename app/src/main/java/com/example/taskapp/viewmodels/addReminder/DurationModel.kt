package com.example.taskapp.viewmodels.addReminder

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.example.taskapp.BR
import com.example.taskapp.MainActivity
import com.example.taskapp.R
import com.example.taskapp.database.entities.Duration
import com.example.taskapp.fragments.addReminder.ReminderDurationState
import com.example.taskapp.utils.Converters
import com.google.android.material.textview.MaterialTextView
import org.threeten.bp.LocalDate
import javax.inject.Inject

class DurationModel @Inject constructor() : BaseObservable() {
    private var durationState: ReminderDurationState = (ReminderDurationState.NoEndDate)
    set(value){
        field = value
        notifyPropertyChanged(BR.dateValid)
    }


    @Bindable
    var currentDaysDuration = 10
        private set(value) {
            field = value
            notifyPropertyChanged(BR.currentDaysDuration)
        }

    @Bindable
    var beginningDate = TODAY
        set(value) {
            if (isBeginningDateValid(value)) {
                field = value
                notifyPropertyChanged(BR.beginningDate)
            } else {
                //todo toast
            }
        }


    @Bindable
    var currentEndDate: LocalDate = LocalDate.ofYearDay(TODAY.year, TODAY.dayOfYear + 10)
        private set(value) {
            if(isEndDateValid(value)){
                field = value
                notifyPropertyChanged(BR.currentEndDate)
            }else{
                //todo toast
            }

        }


    @Bindable
    fun isDateValid(): Boolean {
        return if(durationState is ReminderDurationState.EndDate){
            isEndDateValid()
        }else{
            true
        }
    }

    private fun isEndDateValid(date: LocalDate = currentEndDate) =
        !date.isBefore(beginningDate)


    private fun isBeginningDateValid(date: LocalDate): Boolean {
        val isValid = (date.isEqual(TODAY) || date.isAfter(TODAY))
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


    fun getDuration(): Duration {
        return when (durationState) {
            is ReminderDurationState.EndDate -> {
                Duration(
                    isDate = true,
                    duration = Converters()
                        .localDateToLong(currentEndDate)
                )
            }
            is ReminderDurationState.DaysDuration -> {
                Duration(isDate = false, duration = currentDaysDuration.toLong())
            }
            is ReminderDurationState.NoEndDate -> {
                Duration(isDate = false, duration = 0, noDate = true)
            }
        }
    }


    companion object {
        val TODAY: LocalDate = LocalDate.now()



    }


}