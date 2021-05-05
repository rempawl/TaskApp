package com.example.taskapp.viewmodels.reminder.durationModel

import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskapp.data.reminder.Duration
import com.example.taskapp.utils.DateUtils.TODAY
import com.example.taskapp.viewmodels.reminder.ReminderDurationState
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import org.threeten.bp.LocalDate

class EditTaskDurationModel @AssistedInject constructor(
    @Assisted duration: Duration?,
    @Assisted begDate: LocalDate
) : DurationModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(
            duration: Duration? = null,
            begDate: LocalDate = TODAY
        ): EditTaskDurationModel
    }

    private val _initDurState = MutableLiveData<ReminderViewModel.DurationRadioState>()
    override val initDurState: LiveData<ReminderViewModel.DurationRadioState>
        get() = _initDurState

    override var durationState: ReminderDurationState = ReminderDurationState.NoEndDate
        private set(value) {
            field = value
            notifyPropertyChanged(BR.datesValid)
        }


    init {
        beginningDate = begDate

        if (duration != null) {
            _initDurState.value = when (val durState = duration.convertToDurationState()) {
                is ReminderDurationState.NoEndDate -> {
                    setNoEndDateDurationState()
                    ReminderViewModel.DurationRadioState.NoEndDateDurationState()
                }
                is ReminderDurationState.DaysDuration -> {
                    setDaysDurationState(days = durState.days)
                    ReminderViewModel.DurationRadioState.DaysDurationState()
                }
                is ReminderDurationState.EndDate -> {
                    setEndDateDurationState(endDate = durState.date)
                    ReminderViewModel.DurationRadioState.EndDateDurationState()

                }
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
