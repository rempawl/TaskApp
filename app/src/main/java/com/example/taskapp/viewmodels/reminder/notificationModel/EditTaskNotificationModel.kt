package com.example.taskapp.viewmodels.reminder.notificationModel

import com.example.taskapp.data.reminder.NotificationTime
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.threeten.bp.LocalTime

class EditTaskNotificationModel @AssistedInject constructor(@Assisted notificationTime: NotificationTime?) :
    NotificationModel() {

    @AssistedFactory
    interface Factory {
        fun create(notificationTime: NotificationTime? = null): EditTaskNotificationModel
    }

    override var notificationTime: LocalTime = INITIAL_TIME
        set(value) {
            isNotificationTimeSet.set(true)
            field = value
        }

    init {
        if (notificationTime != null && notificationTime.isSet) {
            this.notificationTime = (notificationTime.convertToLocalTime())
        }
    }

    override fun getNotificationTime(): NotificationTime = NotificationTime.from(
        notificationTime, isNotificationTimeSet.get() as Boolean
    )


}