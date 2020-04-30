package com.example.taskapp.viewmodels.reminder

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskapp.MyApp
import com.example.taskapp.R
import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.utils.scheduler.SchedulerProvider
import com.example.taskapp.viewmodels.reminder.durationModel.DurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.NotificationModel
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.threeten.bp.LocalTime


abstract class ReminderViewModel(
    val task: DefaultTask,
    private val schedulerProvider: SchedulerProvider,
    val notificationModel: NotificationModel,
    val frequencyModel: FrequencyModel,
    val durationModel: DurationModel
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()


    /**
     * when _addedTask is not null then  notification alarm should be set
     */
    private val _addedTask = MutableLiveData<DefaultTask>(null)
    val addedTask: LiveData<DefaultTask>
        get() = _addedTask


    private val _toastText = MutableLiveData<Int>(null)
    val toastText: LiveData<Int>
        get() = _toastText

    private val errorCallback: ErrorCallback

    init {
        errorCallback = ErrorCallback(durationModel, _toastText)
        durationModel.isEndDateError.addOnPropertyChangedCallback(errorCallback)
        durationModel.isBegDateError.addOnPropertyChangedCallback(errorCallback)
    }

    protected abstract suspend fun addTask(task: DefaultTask): Single<Long>

    protected abstract fun createTask(reminder: Reminder?): DefaultTask

    private fun createReminder(): Reminder {
        return Reminder(
            begDate = durationModel.beginningDate,
            duration = durationModel.getDuration(),
            frequency = frequencyModel.getFrequency(),
            notificationTime = notificationModel.getNotificationTime(),
            realizationDate = frequencyModel.getUpdateDate(durationModel.beginningDate),
            expirationDate = durationModel.getExpirationDate()
        )


    }

    suspend fun saveTask(setReminder: Boolean = true) {

            val reminder = if (setReminder) createReminder() else null
            val task = createTask(reminder)
            val isRealizationToday = reminder?.realizationDate?.isEqual(MyApp.TODAY) ?: false

            compositeDisposable.add(
                addTask(task)
                    .subscribeOn(schedulerProvider.getIoScheduler())
                    .observeOn(schedulerProvider.getUiScheduler())
                    .subscribe({ taskID ->// saveStreak(taskID)
                    },
                        { e -> e.printStackTrace() }
                    )
            )
            if (isRealizationToday && reminder != null
                && reminder.notificationTime.convertToLocalTime().isAfter(LocalTime.now())
            ) {
                _addedTask.value = task
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

    override fun onCleared() {
        super.onCleared()
        durationModel.isBegDateError.removeOnPropertyChangedCallback(errorCallback)
        durationModel.isEndDateError.removeOnPropertyChangedCallback(errorCallback)
    }

}

private class ErrorCallback(
    private val durationModel: DurationModel,
    private val toastText: MutableLiveData<Int>
) :
    Observable.OnPropertyChangedCallback() {
    override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
        if (sender != null) {
            val value = (sender as ObservableField<Boolean>).get()
            if (value == true) {
                sender.set(false)
                when (sender) {
                    durationModel.isBegDateError -> {
                        toastText.value = R.string.beginning_date_error
                    }
                    durationModel.isEndDateError -> {
                        toastText.value = R.string.end_date_error
                    }
                }
            } else {
                toastText.value = null
            }
        }
    }
}
