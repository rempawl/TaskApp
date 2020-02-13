package com.example.taskapp.viewmodels.addReminder

import androidx.databinding.BaseObservable
import org.threeten.bp.LocalTime
import javax.inject.Inject

class NotificationModel @Inject constructor() : BaseObservable() {

    var notificationTime: LocalTime = INITIAL_TIME
        private set

    var isNotificationTimeSet = false
        private set


    fun setNotificationTime(time: LocalTime) {
        notificationTime =(time)
        isNotificationTimeSet = true
    }

    companion object {
        val INITIAL_TIME: LocalTime = LocalTime.of(18, 0, 0)

    }

}