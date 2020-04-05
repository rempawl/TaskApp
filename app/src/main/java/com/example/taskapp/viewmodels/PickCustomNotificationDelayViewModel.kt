package com.example.taskapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskapp.fragments.PickCustomNotificationDelayFragment.Companion.DEFAULT_VALUE
import com.example.taskapp.utils.Event
import javax.inject.Inject

class PickCustomNotificationDelayViewModel @Inject constructor() : ViewModel() {
    var delayValue: Int = DEFAULT_VALUE

    private val _broadcastNotHandled: MutableLiveData<Event<Unit>> = MutableLiveData()

    val broadcastNotHandled: LiveData<Event<Unit>>
        get() = _broadcastNotHandled

    fun onBroadcastNotHandled() {
        _broadcastNotHandled.value = Event(Unit)
    }

}
