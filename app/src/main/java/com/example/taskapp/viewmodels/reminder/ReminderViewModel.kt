package com.example.taskapp.viewmodels.reminder

import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.taskapp.MainActivity.Companion.TAG
import com.example.taskapp.MyApp
import com.example.taskapp.database.entities.reminder.Reminder
import com.example.taskapp.database.entities.task.DefaultTask
import com.example.taskapp.utils.providers.SchedulerProvider
import com.example.taskapp.viewmodels.reminder.durationModel.DurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.NotificationModel
import com.example.taskapp.viewmodels.taskDetails.TaskDetailsModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import org.threeten.bp.LocalTime


abstract class ReminderViewModel(
    val taskDetailsModel: TaskDetailsModel,
    val task: DefaultTask,
    val notificationModel: NotificationModel,
    val frequencyModel: FrequencyModel,
    val durationModel: DurationModel
) : ViewModel() {



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

    private val _durationRadioId = MutableLiveData(DAILY_FREQ_RADIO_ID)
    val durationRadioId: LiveData<String>
        get() = _durationRadioId

    val isReminderSwitchChecked = ObservableField(task.reminder != null)

    private val _shouldSetAlarm = MutableLiveData<Pair<Boolean, DefaultTask?>>(Pair(false, null))
    val shouldSetAlarm: LiveData<Pair<Boolean, DefaultTask?>>
        get() = _shouldSetAlarm

    private val _isSetNotifBtnClicked = MutableLiveData(false)
    val isSetNotifBtnClicked: LiveData<Boolean>
        get() = _isSetNotifBtnClicked

    val toastText: LiveData<Int>
        get() = transformError(isError = durationModel.isError)

    val onFocusTaskName: View.OnFocusChangeListener = View.OnFocusChangeListener { view, focused ->
        onFocusChange(view, focused, taskDetailsModel)
    }


    private fun onFocusChange(view: View?, focused: Boolean, taskDetailsModel: TaskDetailsModel) {
        val text = (view as EditText).text.toString()
        if (!focused && text.isNotEmpty()) {
            taskDetailsModel.isTaskNameValid(true)
        }
    }

    fun onFrequencyRadioClick(view: View?, id: String) {
        if (view is RadioButton) {
            _durationRadioId.value = id
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

    fun onDurationRadioCheck(view: View?) {


    }


    fun saveTask() {
        viewModelScope.launch {
            _isConfirmBtnClicked.value = true

            val reminder = if (isReminderSwitchChecked.get() as Boolean) createReminder() else null
            val task = createTask(reminder)
            val isRealizationToday = reminder?.realizationDate?.isEqual(MyApp.TODAY) ?: false

            Log.d(TAG, "$task")
            addTask(task)

            val isAfterNow =
                reminder?.notificationTime?.convertToLocalTime()?.isAfter(LocalTime.now()) ?: false
            if (isRealizationToday && isAfterNow) {
                _shouldSetAlarm.value = Pair(true, task)
            }
        }
    }


    protected abstract suspend fun addTask(task: DefaultTask)

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

    private fun createTask(reminder: Reminder?): DefaultTask {
        return task.copy(
            name = taskDetailsModel.taskName,
            description = taskDetailsModel.taskDescription,
            reminder = reminder
        )
    }


    private fun transformError(isError: LiveData<Int>) =
        Transformations.map(isError) { stringId -> stringId }


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



