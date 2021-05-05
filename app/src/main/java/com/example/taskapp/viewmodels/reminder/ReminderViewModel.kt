package com.example.taskapp.viewmodels.reminder

import android.view.View
import android.widget.EditText
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.taskapp.data.reminder.Reminder
import com.example.taskapp.data.task.Task
import com.example.taskapp.utils.DateUtils.TODAY
import com.example.taskapp.viewmodels.reminder.durationModel.DurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.NotificationModel
import com.example.taskapp.viewmodels.taskDetails.TaskDetailsModel
import kotlinx.coroutines.launch
import org.threeten.bp.LocalTime


abstract class ReminderViewModel(
    val taskDetailsModel: TaskDetailsModel,
    val task: Task,
    val notificationModel: NotificationModel,
    val frequencyModel: FrequencyModel,
    val durationModel: DurationModel
) : ViewModel() {

    sealed class FrequencyRadioState {
        abstract val id: String

        data class DailyFreqRadioState(override val id: String = DAILY_FREQ_RADIO_ID) :
            FrequencyRadioState()

        data class DaysOfWeekRadio(override val id: String = DAYS_OF_WEEK_FREQ_RADIO_ID) :
            FrequencyRadioState()
    }

    sealed class DurationRadioState {
        abstract val id: String

        data class EndDateDurationState(override val id: String = END_DATE_DURATION_RADIO_ID) :
            DurationRadioState()

        data class NoEndDateDurationState(override val id: String = NO_END_DATE_DURATION_RADIO_ID) :
            DurationRadioState()

        data class DaysDurationState(override val id: String = DAYS_DURATION_RADIO_ID) :
            DurationRadioState()
    }


    val getDailyFreqRadioState
        get() = FrequencyRadioState.DailyFreqRadioState()

    val getDaysOfWeekFreqRadioState
        get() = FrequencyRadioState.DaysOfWeekRadio()

    val getDaysDurationRadioState
        get() = DurationRadioState.DaysDurationState()

    val getEndDateDurationRadioState
        get() = DurationRadioState.EndDateDurationState()

    val getNoEndDateDurationRadioState
        get() = DurationRadioState.NoEndDateDurationState()

    private val _isBeginningDateBtnClicked = MutableLiveData(false)
    val isBeginningDateBtnClicked: LiveData<Boolean>
        get() = _isBeginningDateBtnClicked

    private val _isEndDateBtnClicked = MutableLiveData(false)
    val isEndDateBtnClicked: LiveData<Boolean>
        get() = _isEndDateBtnClicked

    private val _isDurationDaysBtnClicked = MutableLiveData(false)
    val isDurationDaysBtnClicked: LiveData<Boolean>
        get() = _isDurationDaysBtnClicked

    private val _isConfirmBtnClicked = MutableLiveData(false)
    val isConfirmBtnClicked: LiveData<Boolean>
        get() = _isConfirmBtnClicked

    private val _isSetDaysOfWeekBtnClicked = MutableLiveData(false)
    val isSetDaysOfWeekBtnClicked: LiveData<Boolean>
        get() = _isSetDaysOfWeekBtnClicked

    private val _isSetDailyFreqBtnClicked = MutableLiveData(false)
    val isSetDailyFreqBtnClicked: LiveData<Boolean>
        get() = _isSetDailyFreqBtnClicked

    private val _frequencyRadioState =
        MutableLiveData<FrequencyRadioState>(FrequencyRadioState.DailyFreqRadioState())

    val frequencyRadioState: LiveData<FrequencyRadioState>
        get() = _frequencyRadioState

    private val _durationRadioState =
        MutableLiveData<DurationRadioState>(DurationRadioState.NoEndDateDurationState())
    val durationRadioState: LiveData<DurationRadioState>
        get() = _durationRadioState

    val isReminderSwitchChecked = ObservableField(task.reminder != null)

    private val _shouldSetAlarm = MutableLiveData<Pair<Boolean, Task?>>(Pair(false, null))
    val shouldSetAlarm: LiveData<Pair<Boolean, Task?>>
        get() = _shouldSetAlarm

    private val _isSetNotifBtnClicked = MutableLiveData(false)
    val isSetNotifBtnClicked: LiveData<Boolean>
        get() = _isSetNotifBtnClicked

    val toastText: LiveData<Int?>
        get() = Transformations.map(durationModel.isError) { stringId -> stringId }

    val onFocusTaskName: View.OnFocusChangeListener = View.OnFocusChangeListener { view, focused ->
        onFocusChange(view, focused, taskDetailsModel)
    }

    private val freqObs =
        Observer<FrequencyRadioState> { state -> _frequencyRadioState.value = state }
    private val durObs = Observer<DurationRadioState> { state -> _durationRadioState.value = state }

    init {
        frequencyModel.initFreqState.observeForever(freqObs)
        durationModel.initDurState.observeForever(durObs)
    }

    override fun onCleared() {
        super.onCleared()
        frequencyModel.initFreqState.removeObserver(freqObs)
        durationModel.initDurState.removeObserver(durObs)
    }

    fun onFrequencyRadioClick(freqState: FrequencyRadioState) {
        _frequencyRadioState.value = freqState
        when (freqState) {
            is FrequencyRadioState.DailyFreqRadioState -> {
                frequencyModel.setDailyFrequency()
            }
            is FrequencyRadioState.DaysOfWeekRadio -> {
                frequencyModel.setDaysOfWeekFrequency(daysOfWeek = null)
            }
        }
    }

    fun onDurationRadioCheck(state: DurationRadioState) {
        _durationRadioState.value = state
        when (state) {
            is DurationRadioState.NoEndDateDurationState -> durationModel.setNoEndDateDurationState()
            is DurationRadioState.EndDateDurationState -> durationModel.setEndDateDurationState()
            is DurationRadioState.DaysDurationState -> durationModel.setDaysDurationState()
        }

    }

    fun onBegDateBtnClick() {
        _isBeginningDateBtnClicked.value = true
    }

    fun onBegDateDialogShow() {
        _isBeginningDateBtnClicked.value = false
    }

    fun onEndDateBtnClick() {
        _isEndDateBtnClicked.value = true
    }

    fun onEndDateDialogShow() {
        _isEndDateBtnClicked.value = false
    }

    fun onDurationDaysBtnClick() {
        _isDurationDaysBtnClicked.value = true
    }

    fun onDurationDaysPickerDialogShow() {
        _isDurationDaysBtnClicked.value = false
    }

    fun onSaveTaskFinished() {
        _isConfirmBtnClicked.value = false
    }


    fun onSetNotifBtnClick() {
        _isSetNotifBtnClicked.value = true
    }

    fun onNotifDialogShow() {
        _isSetNotifBtnClicked.value = false
    }

    fun onDailyFreqDialogShow() {
        _isSetDailyFreqBtnClicked.value = false
    }

    fun onSetDailyFreqBtnClick() {
        _isSetDailyFreqBtnClicked.value = true
    }

    fun onDaysOfWeekDialogShow() {
        _isSetDaysOfWeekBtnClicked.value = false
    }

    fun onSetDaysOfWeekBtnClick() {
        _isSetDaysOfWeekBtnClicked.value = true
    }


    fun saveTask() {
        viewModelScope.launch {
            _isConfirmBtnClicked.value = true

            val isChckd = isReminderSwitchChecked.get() ?: false
            val reminder = if (isChckd) createReminder() else null
            val task = createTask(reminder)
            val isRealizationToday = reminder?.realizationDate?.isEqual(TODAY) ?: false

            addTask(task)

            val isAfterNow =
                reminder?.notificationTime?.convertToLocalTime()?.isAfter(LocalTime.now()) ?: false
            if (isRealizationToday && isAfterNow) {
                _shouldSetAlarm.value = Pair(true, task)
            }
        }
    }


    protected abstract suspend fun addTask(task: Task)

    private fun createReminder(): Reminder {
        return Reminder(
            begDate = durationModel.beginningDate,
            duration = durationModel.getDuration(),
            frequency = frequencyModel.getFrequency(),
            notificationTime = notificationModel.getNotificationTime(),
            realizationDate = frequencyModel.getRealizationDate(begDate = durationModel.beginningDate),
            expirationDate = durationModel.getExpirationDate()
        )

    }

    private fun createTask(reminder: Reminder?): Task {
        return task.copy(
            name = taskDetailsModel.taskName,
            description = taskDetailsModel.taskDescription,
            reminder = reminder
        )
    }

    private fun onFocusChange(view: View?, focused: Boolean, taskDetailsModel: TaskDetailsModel) {
        val text = (view as EditText).text.toString()
        if (!focused && text.isNotEmpty()) {
            taskDetailsModel.isTaskNameValid(true)
        }
    }


/*
    private fun saveStreak(taskID: Long) {
        //todo
//        val streak =
//            Streak(parentTaskID = taskID, duration = 0, begDate = LocalDate.now())
//        streakLocalDataSource.saveStreak(streak)
    }
*/


    companion object {

        const val DAILY_FREQ_RADIO_ID = "daily_freq_radio_btn"
        const val DAYS_OF_WEEK_FREQ_RADIO_ID = "daysOfWeek_freq_radio_btn"
        const val DAYS_DURATION_RADIO_ID = "days_duration_radio_btn"
        const val END_DATE_DURATION_RADIO_ID = "endDate_duration_radio_btn"
        const val NO_END_DATE_DURATION_RADIO_ID = "noEndDate_duration_radio_btn"
    }
}






