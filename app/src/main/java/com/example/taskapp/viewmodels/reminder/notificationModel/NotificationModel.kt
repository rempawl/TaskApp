package com.example.taskapp.viewmodels.reminder.notificationModel

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import com.example.taskapp.database.entities.NotificationTime
import org.threeten.bp.LocalTime

abstract class NotificationModel constructor(notificationTime: NotificationTime?) :
    BaseObservable() {

    var notificationTime: LocalTime =   INITIAL_TIME
        set(value) {
            isNotificationTimeSet.set(true)
            field = value
        }

    val isNotificationTimeSet = ObservableField(false)

    init {
        if (notificationTime != null && notificationTime.isSet) {
            this.notificationTime = (notificationTime.convertToLocalTime())
        }
    }

    fun getNotificationTime(): NotificationTime = NotificationTime.from(
        notificationTime, isNotificationTimeSet.get() as Boolean
    )

    companion object {
        val INITIAL_TIME: LocalTime = LocalTime.of(18, 0, 0)
    }
}