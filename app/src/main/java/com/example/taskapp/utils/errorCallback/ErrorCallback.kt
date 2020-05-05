package com.example.taskapp.utils.errorCallback

import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData

abstract class ErrorCallback(
    protected val toastText: MutableLiveData<Int>
) : Observable.OnPropertyChangedCallback()
