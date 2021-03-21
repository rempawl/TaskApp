package com.example.taskapp.viewmodels.reminder.notificationModel

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import com.example.taskapp.data.reminder.NotificationTime
import org.threeten.bp.LocalTime

abstract class NotificationModel :BaseObservable() {

    abstract var notificationTime: LocalTime

     val isNotificationTimeSet : ObservableField<Boolean> = ObservableField(false)

    abstract fun getNotificationTime(): NotificationTime

    companion object {
        val INITIAL_TIME: LocalTime = LocalTime.of(18, 0, 0)
    }

}


