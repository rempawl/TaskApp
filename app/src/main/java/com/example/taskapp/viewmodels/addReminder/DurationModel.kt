package com.example.taskapp.viewmodels.addReminder

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.example.taskapp.BR
import com.example.taskapp.fragments.addReminder.ReminderDurationState
import com.google.android.material.button.MaterialButton
import org.threeten.bp.LocalDate
import javax.inject.Inject

class DurationModel @Inject constructor() : BaseObservable() {


    //    @Bindable
//    fun isValid() : Boolean{
//        isBeginningDateValid()
//    }
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
                //todo show error
            }
        }

    @Bindable
    var currentEndDate: LocalDate = LocalDate.ofYearDay(TODAY.year, TODAY.dayOfYear + 10)
        private set(value) {
            field = value
            notifyPropertyChanged(BR.currentEndDate)
        }


    private var durationState: ReminderDurationState = (ReminderDurationState.NoEndDate)

    private fun isEndDateValid(date: LocalDate) = date.isAfter(TODAY) &&
            date.isAfter(beginningDate)


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
        if (isEndDateValid(endDate)) {
            durationState = ReminderDurationState.EndDate(endDate)
            currentEndDate = endDate
        } else {
            //todo show error
        }

    }


    companion object {
        val TODAY: LocalDate = LocalDate.now()
        @JvmStatic
        @BindingAdapter("error")
        fun setError(view: MaterialButton, stringOrRsrcID: Any?) {
            if (stringOrRsrcID != null) {
                when (stringOrRsrcID) {
                    is String -> view.error = stringOrRsrcID
                    is Int -> view.apply {
                        val text = context.resources.getString(stringOrRsrcID)
                        error = text
                    }
                }
            }

        }

    }


}