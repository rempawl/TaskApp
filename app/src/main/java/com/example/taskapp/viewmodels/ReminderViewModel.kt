package com.example.taskapp.viewmodels

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskapp.R
import com.example.taskapp.viewmodels.reminder.DurationModel
import com.example.taskapp.viewmodels.reminder.FrequencyModel
import com.example.taskapp.viewmodels.reminder.NotificationModel
import javax.inject.Inject



@Suppress("UNCHECKED_CAST")
open class ReminderViewModel @Inject constructor(
    protected val durationModelFactory: DurationModel.Factory,
    protected val frequencyModelFactory: FrequencyModel.Factory,
    private val notificationModelFactory: NotificationModel.Factory
) : ViewModel() {

    open val notificationModel = notificationModelFactory.create()
    open val durationModel = durationModelFactory.create()
    open val frequencyModel: FrequencyModel = frequencyModelFactory.create()
    private val errorCallback by lazy(LazyThreadSafetyMode.NONE) {

        object : Observable.OnPropertyChangedCallback() {
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
    }


    init {
        durationModel.endDateError.addOnPropertyChangedCallback(errorCallback)
        durationModel.begDateError.addOnPropertyChangedCallback(errorCallback)
    }


    private val toastText = MutableLiveData<Int>(null)
    fun getToastText(): LiveData<Int> = toastText

    override fun onCleared() {
        super.onCleared()
        durationModel.begDateError.removeOnPropertyChangedCallback(errorCallback)
        durationModel.endDateError.removeOnPropertyChangedCallback(errorCallback)
    }

}
