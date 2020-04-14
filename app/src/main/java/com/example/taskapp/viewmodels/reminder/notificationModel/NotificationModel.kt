package com.example.taskapp.viewmodels.reminder.notificationModel

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import com.example.taskapp.database.entities.NotificationTime
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import org.threeten.bp.LocalTime
import javax.inject.Inject

abstract class NotificationModel :BaseObservable() {

    abstract var notificationTime: LocalTime

    abstract val isNotificationTimeSet : ObservableField<Boolean>


    abstract fun getNotificationTime(): NotificationTime

    companion object {
        val INITIAL_TIME: LocalTime = LocalTime.of(18, 0, 0)
    }

}

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

class EditTaskNotificationModel @AssistedInject constructor(@Assisted notificationTime: NotificationTime?) :
    NotificationModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(notificationTime: NotificationTime? = null): EditTaskNotificationModel
    }

    override var notificationTime: LocalTime = INITIAL_TIME
        set(value) {
            isNotificationTimeSet.set(true)
            field = value
        }

    override val isNotificationTimeSet = ObservableField(false)

    override fun getNotificationTime(): NotificationTime = NotificationTime.from(
        notificationTime, isNotificationTimeSet.get() as Boolean
    )

    init {
        if (notificationTime != null && notificationTime.isSet) {
            this.notificationTime = (notificationTime.convertToLocalTime())
        }
    }




}