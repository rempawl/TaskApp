package com.example.taskapp.utils.notification

import android.content.Intent
import com.example.taskapp.database.entities.TaskMinimal

interface NotificationIntentFactory {

    fun createDelayNotificationIntent(delayValue: Int,task: TaskMinimal): Intent
    fun createNotificationReceiverIntent(task: TaskMinimal): Intent
}
