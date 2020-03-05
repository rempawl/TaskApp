package com.example.taskapp.viewmodels

import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskapp.MainActivity
import com.example.taskapp.R
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.database.entities.Task
import com.example.taskapp.repos.task.TaskRepository
import com.example.taskapp.viewmodels.addTask.TaskDetails
import com.example.taskapp.viewmodels.reminder.DurationModel
import com.example.taskapp.viewmodels.reminder.FrequencyModel
import com.example.taskapp.viewmodels.reminder.NotificationModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class AddReminderViewModel @AssistedInject constructor(
    @Assisted val taskDetails: TaskDetails,
    private val taskRepository: TaskRepository,
    durationModelFactory: DurationModel.Factory,
    notificationModelFactory: NotificationModel.Factory,
    frequencyModelFactory: FrequencyModel.Factory
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(taskDetails: TaskDetails): AddReminderViewModel
    }

    private val compositeDisposable  = CompositeDisposable()

    fun saveTaskWithReminder() {
        viewModelScope.launch {

            val reminder = Reminder(
                begDate = durationModel.beginningDate,
                duration = durationModel.getDuration(),
                frequency = frequencyModel.getFrequency(),
                notificationTime = notificationModel.getNotificationTime(),
                notificationDate = frequencyModel.getUpdateDate(durationModel.beginningDate),
                expirationDate = durationModel.getExpirationDate()
            )

            compositeDisposable.add(taskRepository.saveTask(
                Task(
                    name = taskDetails.name, description = taskDetails.description,
                    reminder = reminder
                )
            ).subscribeOn(Schedulers.io())
                .subscribe(
                    { Log.d(MainActivity.TAG, it.toString()) },
                    { e -> e.printStackTrace() })
            )
            //todo make stats entity
        }
    }

    val notificationModel = notificationModelFactory.create()
    val durationModel = durationModelFactory.create()
    val frequencyModel: FrequencyModel = frequencyModelFactory.create()

    private val toastText = MutableLiveData<Int>(null)
    fun getToastText(): LiveData<Int> = toastText

    private val errorCallback = ErrorCallback(durationModel, toastText)

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


    companion object
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
