package com.example.taskapp.viewmodels.reminder.notificationModel

import com.example.taskapp.database.entities.NotificationTime
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class DefaultNotificationModel @AssistedInject constructor(@Assisted notificationTime: NotificationTime?) :
     NotificationModel(notificationTime) {

    @AssistedInject.Factory
    interface Factory {
        fun create(notificationTime: NotificationTime? = null): NotificationModel
    }
}