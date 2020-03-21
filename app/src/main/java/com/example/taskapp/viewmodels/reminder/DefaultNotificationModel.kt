package com.example.taskapp.viewmodels.reminder

import androidx.databinding.ObservableField
import com.example.taskapp.database.entities.NotificationTime
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import org.threeten.bp.LocalTime

class DefaultNotificationModel @AssistedInject constructor(@Assisted notificationTime: NotificationTime?) :
     NotificationModel(){

    @AssistedInject.Factory
    interface Factory {
        fun create(notificationTime: NotificationTime? = null): DefaultNotificationModel
    }



    var notificationTime: LocalTime = INITIAL_TIME
        private set

    val isNotificationTimeSet = ObservableField(false)

    init {
        if (notificationTime != null && notificationTime.isSet) {
            setNotificationTime(notificationTime.convertToLocalTime())
        }
    }

    fun setNotificationTime(time: LocalTime) {
        notificationTime = (time)
        isNotificationTimeSet.set( true)
    }

    fun getNotificationTime(): NotificationTime = NotificationTime(
        notificationTime.hour,
        notificationTime.minute, isNotificationTimeSet.get() as Boolean
    )

    companion object {
        val INITIAL_TIME: LocalTime = LocalTime.of(18, 0, 0)

    }

}