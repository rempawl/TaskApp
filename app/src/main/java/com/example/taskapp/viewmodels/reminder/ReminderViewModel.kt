package com.example.taskapp.viewmodels.reminder

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskapp.R
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.database.entities.Task
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.viewmodels.reminder.durationModel.DefaultDurationModel
import com.example.taskapp.viewmodels.reminder.durationModel.DurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.DefaultFrequencyModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.DefaultNotificationModel
import com.example.taskapp.viewmodels.reminder.notificationModel.NotificationModel
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch


abstract class ReminderViewModel(
    val task: Task,
    protected val taskRepository: TaskRepositoryInterface,
    defaultDurationModelFactory: DefaultDurationModel.Factory,
    defaultNotificationModelFactory: DefaultNotificationModel.Factory,
    frequencyModelFactory: DefaultFrequencyModel.Factory

) : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    val notificationModel: NotificationModel
    val frequencyModel: FrequencyModel
    val durationModel: DurationModel

    init {
        val reminder = task.reminder
        if (reminder != null) {
            durationModel = defaultDurationModelFactory.create(reminder.duration, reminder.begDate)
            frequencyModel = frequencyModelFactory.create(reminder.frequency)
            notificationModel = defaultNotificationModelFactory.create(reminder.notificationTime)
        } else {
            notificationModel = defaultNotificationModelFactory.create()
            durationModel = defaultDurationModelFactory.create()
            frequencyModel = frequencyModelFactory.create()

        }
    }

    private val _toastText = MutableLiveData<Int>(null)
    val toastText: LiveData<Int>
        get() = _toastText

    private val errorCallback: ErrorCallback

    init {
        errorCallback = ErrorCallback(durationModel, _toastText)
        durationModel.endDateError.addOnPropertyChangedCallback(errorCallback)
        durationModel.begDateError.addOnPropertyChangedCallback(errorCallback)
    }

    protected suspend fun addTask(task: Task): Single<Long> = taskRepository.saveTask(task)

    protected fun createTask(reminder: Reminder): Task {
        return task.copy(reminder = reminder)

    }

    protected fun createReminder(): Reminder {
        return Reminder(
            begDate = durationModel.beginningDate,
            duration = durationModel.getDuration(),
            frequency = frequencyModel.getFrequency(),
            notificationTime = notificationModel.getNotificationTime(),
            realizationDate = frequencyModel.getUpdateDate(durationModel.beginningDate),
            expirationDate = durationModel.getExpirationDate()
        )


    }

    private fun saveStreak(taskID: Long) = viewModelScope.launch {
        //todo
//        val streak =
//            Streak(parentTaskID = taskID, duration = 0, begDate = LocalDate.now())
//        streakLocalDataSource.saveStreak(streak)
    }

    override fun onCleared() {
        super.onCleared()
        durationModel.begDateError.removeOnPropertyChangedCallback(errorCallback)
        durationModel.endDateError.removeOnPropertyChangedCallback(errorCallback)
    }

}

class ErrorCallback(
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
                    durationModel.begDateError -> {
                        toastText.value = R.string.beginning_date_error
                    }
                    durationModel.endDateError -> {
                        toastText.value = R.string.end_date_error
                    }
                }
            } else {
                toastText.value = null
            }
        }
    }
}
