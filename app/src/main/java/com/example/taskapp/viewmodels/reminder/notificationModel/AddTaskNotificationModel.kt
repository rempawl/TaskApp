package com.example.taskapp.viewmodels.reminder.notificationModel

import com.example.taskapp.database.entities.reminder.NotificationTime
import org.threeten.bp.LocalTime
import javax.inject.Inject

class AddTaskNotificationModel @Inject constructor() : NotificationModel() {

    override var notificationTime: LocalTime = INITIAL_TIME
        set(value) {
            isNotificationTimeSet.set(true)
            field = value
        }

    override fun getNotificationTime(): NotificationTime = NotificationTime.from(
        notificationTime, isNotificationTimeSet.get() as Boolean
    )


}
