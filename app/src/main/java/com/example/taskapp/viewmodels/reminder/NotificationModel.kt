package com.example.taskapp.viewmodels.reminder

import androidx.databinding.BaseObservable
import com.example.taskapp.database.entities.NotificationTime
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import org.threeten.bp.LocalTime

class NotificationModel @AssistedInject constructor(@Assisted notificationTime : NotificationTime?)
    : BaseObservable() {

    @AssistedInject.Factory
    interface Factory{
        fun create(notificationTime: NotificationTime? = null) : NotificationModel
    }

    init{
        if(notificationTime != null && notificationTime.isSet){
            setNotificationTime(notificationTime.convertToLocalTime())
        }
    }

    var notificationTime: LocalTime =
        INITIAL_TIME
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