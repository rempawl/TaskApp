package com.example.taskapp.utils.errorCallback

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.taskapp.R
import com.example.taskapp.viewmodels.reminder.durationModel.DurationModel

class DurationErrorCallback(private val durationModel: DurationModel, toastText: MutableLiveData<Int>)
    :ErrorCallback(toastText){
    override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
        if (sender != null) {
           @Suppress("UNCHECKED_CAST") val value = (sender as ObservableField<Boolean>).get()
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
