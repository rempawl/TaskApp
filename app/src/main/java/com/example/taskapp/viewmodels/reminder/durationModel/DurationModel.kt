package com.example.taskapp.viewmodels.reminder.durationModel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.library.baseAdapters.BR
import com.example.taskapp.MyApp.Companion.TODAY
import com.example.taskapp.database.entities.Duration
import com.example.taskapp.viewmodels.reminder.ReminderDurationState
import org.threeten.bp.LocalDate


abstract class DurationModel : BaseObservable() {


    abstract val durationState: ReminderDurationState

    //true when error message should be displayed
    val begDateError = ObservableField<Boolean>(false)
    val endDateError = ObservableField<Boolean>(false)

    @Bindable
    var currentDaysDuration = 10
        protected set(value) {
            field = value
            notifyPropertyChanged(BR.currentDaysDuration)
        }

    @Bindable
    var beginningDate = TODAY
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
        protected set(value) {
            if (isEndDateValid(value)) {
                field = value
                notifyPropertyChanged(BR.currentEndDate)
                endDateError.set(false)
            } else {
                endDateError.set(true)
            }

        }



    abstract fun isDateValid(): Boolean

    protected abstract fun isEndDateValid(date: LocalDate = currentEndDate): Boolean

    protected abstract fun isBeginningDateValid(date: LocalDate): Boolean

    abstract fun setNoEndDateDurationState()

    abstract fun setDaysDurationState(days: Int = currentDaysDuration)

    abstract fun setEndDateDurationState(endDate: LocalDate = currentEndDate)

    fun getDuration(): Duration = durationState.convertToDuration()

    fun getExpirationDate() = durationState.calculateEndDate(beginningDate)

    companion object


}