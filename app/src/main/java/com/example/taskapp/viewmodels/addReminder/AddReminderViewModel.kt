package com.example.taskapp.viewmodels.addReminder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskapp.MainActivity
import com.example.taskapp.fragments.addReminder.ReminderDurationState
import com.example.taskapp.fragments.addReminder.ReminderFrequencyState
import com.example.taskapp.viewmodels.addTask.TaskDetails
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime


/**
 * This [ViewModel] is shared between all addReminder package  Fragments
 * */
class AddReminderViewModel @AssistedInject constructor(
    @Assisted  val taskDetails: TaskDetails
) : ViewModel() {
    init {
        Log.d(MainActivity.TAG, "New ViewModel")
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(taskDetails: TaskDetails) =
            AddReminderViewModel(
                taskDetails
            )
    }

    private val beginningDate: MutableLiveData<LocalDate> by lazy {
        MutableLiveData<LocalDate>(LocalDate.now())
    }

    fun getBeginningDate(): LiveData<LocalDate> = beginningDate
    fun setBeginningDate(begDate: LocalDate) {
        beginningDate.value = begDate
    }

    var currentBegDate: LocalDate = LocalDate.now()


    private val durationState: MutableLiveData<ReminderDurationState> = MutableLiveData(
        ReminderDurationState.NoEndDate
    )

    fun getDurationState(): LiveData<ReminderDurationState> = durationState
    fun setDurationState(durState: ReminderDurationState) {
        durationState.value = durState
    }

    var currentDaysDuration = 10
    var currentEndDate: LocalDate
    init {
        val d = LocalDate.now()
        currentEndDate = LocalDate.ofYearDay(d.year, d.dayOfYear + 10)
    }


    private val frequency: MutableLiveData<ReminderFrequencyState> =
        MutableLiveData(ReminderFrequencyState.Daily())

    fun setFrequency(state: ReminderFrequencyState) {
        frequency.value = state
    }

    fun getFrequencyState(): LiveData<ReminderFrequencyState> = frequency

    var currentDailyFrequency = ReminderFrequencyState.INITIAL_FREQUENCY
    var currentWeekDays = ReminderFrequencyState.WeekDays().days

    private val notificationTime by lazy {
        MutableLiveData<LocalTime>(INITIAL_TIME)
    }

    fun getNotificationTime(): LiveData<LocalTime> = notificationTime
    fun setNotificationTime(time: LocalTime) {
        notificationTime.value = time
    }

    var currentNotificationTime =
        INITIAL_TIME

    companion object {
        val INITIAL_TIME = LocalTime.of(18, 0, 0)

    }
}
