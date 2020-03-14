package com.example.taskapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.taskapp.fragments.PickCustomNotificationDelayFragment.Companion.DEFAULT_VALUE
import javax.inject.Inject

class PickCustomNotificationDelayViewModel @Inject constructor(): ViewModel() {
    var delayValue :Int = DEFAULT_VALUE
}
