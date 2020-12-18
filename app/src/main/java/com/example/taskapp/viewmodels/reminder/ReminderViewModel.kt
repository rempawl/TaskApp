package com.example.taskapp.viewmodels.reminder

import android.util.Log
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
    private val schedulerProvider: SchedulerProvider,
    val notificationModel: NotificationModel,
    val frequencyModel: FrequencyModel,
    val durationModel: DurationModel
) : ViewModel() {

    private val disposables = CompositeDisposable()

    abstract val isConfirmBtnClicked: LiveData<Boolean>

    val isReminderSwitchChecked = ObservableField(task.reminder != null)

    private val _shouldSetAlarm = MutableLiveData<Pair<Boolean, DefaultTask?>>(Pair(false, null))
    val shouldSetAlarm: LiveData<Pair<Boolean, DefaultTask?>>
        get() = _shouldSetAlarm

    // when _addedTask is not null then  notification alarm should be set
//    private val _addedTask = MutableLiveData<DefaultTask>(null)
//    val addedTask: LiveData<DefaultTask>
//        get() = _addedTask

    val toastText: LiveData<Int>
        get() = transformError(isError = durationModel.isError)

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    abstract fun onSaveTaskFinished()

    fun saveTask() {
        viewModelScope.launch {
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


}



