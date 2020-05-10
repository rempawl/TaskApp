package com.example.taskapp.viewmodels.reminder.durationModel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskapp.MyApp.Companion.TODAY
import com.example.taskapp.R
import com.example.taskapp.database.entities.Duration
import com.example.taskapp.viewmodels.reminder.ReminderDurationState
import org.threeten.bp.LocalDate


abstract class DurationModel : BaseObservable() {

    abstract val durationState: ReminderDurationState



    private val _isError = MutableLiveData<Int>()
    val isError: LiveData<Int>
        get() = _isError

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
                _isError.value = null
            } else {
                _isError.value = (R.string.beginning_date_error)
            }
            notifyPropertyChanged(BR.datesValid)
            field = value
            notifyPropertyChanged(BR.beginningDate)
        }


    @Bindable
    var currentEndDate: LocalDate = LocalDate.ofYearDay(TODAY.year, TODAY.dayOfYear + 10)
        protected set(value) {
            field = value
            notifyPropertyChanged(BR.currentEndDate)
            notifyPropertyChanged(BR.datesValid)

            if (isEndDateValid(value)) {
                _isError.value = null
            } else {
                _isError.value = R.string.end_date_error
            }
        }

    @Bindable
    fun isDatesValid(): Boolean {
        return if (durationState is ReminderDurationState.EndDate) {
            isEndDateValid() && isBeginningDateValid()
        } else {
            isBeginningDateValid()
        }

    }

    private fun isEndDateValid(date: LocalDate = currentEndDate) =
        !date.isBefore(beginningDate) && !date.isBefore(TODAY)

    protected abstract fun isBeginningDateValid(date: LocalDate = beginningDate): Boolean


    abstract fun setNoEndDateDurationState()

    abstract fun setDaysDurationState(days: Int = currentDaysDuration)

    abstract fun setEndDateDurationState(endDate: LocalDate = currentEndDate)

    fun getDuration(): Duration = durationState.convertToDuration()

    fun getExpirationDate() = durationState.calculateEndDate(beginningDate)

    companion object


}