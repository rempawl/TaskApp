package com.example.taskapp.viewmodels.reminder.notificationModel

import androidx.databinding.ObservableField
import com.example.taskapp.database.entities.NotificationTime
import org.threeten.bp.LocalTime
import javax.inject.Inject

class AddTaskNotificationModel @Inject constructor() : NotificationModel(){
    override var notificationTime: LocalTime = INITIAL_TIME
        set(value) {
            isNotificationTimeSet.set(true)
            field = value
        }

    override val isNotificationTimeSet = ObservableField(false)

    override fun getNotificationTime(): NotificationTime = NotificationTime.from(
        notificationTime, isNotificationTimeSet.get() as Boolean
    )


}
