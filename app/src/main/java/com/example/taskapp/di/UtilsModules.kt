package com.example.taskapp.di

import android.content.Context
import com.example.taskapp.utils.alarmCreator.AlarmCreator
import com.example.taskapp.utils.alarmCreator.AlarmCreatorImpl
import com.example.taskapp.utils.notification.NotificationIntentFactory
import com.example.taskapp.utils.notification.NotificationIntentFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
interface UtilsModuleInterface {

    @Reusable
    @Binds
     fun provideAlarmCreator(alarmCreatorImpl: AlarmCreatorImpl): AlarmCreator

}

@Module
object UtilsModule{

    @JvmStatic
    @Provides
    @Reusable
    fun provideNotificationIntentFactory(context: Context): NotificationIntentFactory =
        NotificationIntentFactoryImpl(context)

}

