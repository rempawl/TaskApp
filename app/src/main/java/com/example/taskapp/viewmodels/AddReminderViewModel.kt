package com.example.taskapp.viewmodels

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskapp.R
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.database.entities.Task
import com.example.taskapp.repos.streak.StreakDataSource
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.viewmodels.addTask.TaskDetails
import com.example.taskapp.viewmodels.reminder.DurationModel
import com.example.taskapp.viewmodels.reminder.FrequencyModel
import com.example.taskapp.viewmodels.reminder.NotificationModel
import com.example.taskapp.workers.AlarmCreator
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime


class AddReminderViewModel @AssistedInject constructor(
    @Assisted val taskDetails: TaskDetails,
    private val taskRepository: TaskRepositoryInterface,
    private val streakLocalDataSource: StreakDataSource,
    private val alarmCreator: AlarmCreator,
    durationModelFactory: DurationModel.Factory,
    notificationModelFactory: NotificationModel.Factory,
    frequencyModelFactory: FrequencyModel.Factory
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(taskDetails: TaskDetails): AddReminderViewModel
    }

    private val compositeDisposable = CompositeDisposable()

    val notificationModel = notificationModelFactory.create()
    val durationModel = durationModelFactory.create()
    val frequencyModel: FrequencyModel = frequencyModelFactory.create()

    private val _toastText = MutableLiveData<Int>(null)
    val toastText: LiveData<Int>
        get() = _toastText


    private val errorCallback = ErrorCallback(durationModel, _toastText)

    init {
        durationModel.endDateError.addOnPropertyChangedCallback(errorCallback)
        durationModel.begDateError.addOnPropertyChangedCallback(errorCallback)
    }


    override fun onCleared() {
        super.onCleared()
        durationModel.begDateError.removeOnPropertyChangedCallback(errorCallback)
        durationModel.endDateError.removeOnPropertyChangedCallback(errorCallback)
        compositeDisposable.dispose()
    }

    fun saveTaskWithReminder() {
        viewModelScope.launch {
            val reminder = Reminder(
                begDate = durationModel.beginningDate,
                duration = durationModel.getDuration(),
                frequency = frequencyModel.getFrequency(),
                notificationTime = notificationModel.getNotificationTime(),
                realizationDate = frequencyModel.getUpdateDate(durationModel.beginningDate),
                expirationDate = durationModel.getExpirationDate()
            )

            val task = Task(
                name = taskDetails.name, description = taskDetails.description,
                reminder = reminder
            )
            val isRealizationToday = reminder.realizationDate.isEqual(TODAY)
            var isSuccess = false

            compositeDisposable.add(
                taskRepository.saveTask(task)
                    .subscribeOn(Schedulers.io())
                    .subscribe({ taskID ->
                        isSuccess = true
                        saveStreak(taskID)
                    },
                        { e -> e.printStackTrace() }
                    )
            )
            if (isRealizationToday
                && reminder.notificationTime.convertToLocalTime().isAfter(LocalTime.now())
            ) {
                alarmCreator.setTaskNotificationAlarm(task, true)
            }


        }
    }

    private fun saveStreak(taskID: Long) = viewModelScope.launch {
        //todo
//        val streak =
//            Streak(parentTaskID = taskID, duration = 0, begDate = LocalDate.now())
//        streakLocalDataSource.saveStreak(streak)
    }


    companion object {
        val TODAY: LocalDate = LocalDate.now()
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
