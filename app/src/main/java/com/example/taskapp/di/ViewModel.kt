@file:Suppress("UNCHECKED_CAST")

package com.example.taskapp.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskapp.utils.DefaultSchedulerProvider
import com.example.taskapp.utils.SchedulerProvider
import com.example.taskapp.viewmodels.addTask.DefaultTaskDetailsModel
import com.example.taskapp.viewmodels.addTask.TaskDetailsModel
import com.example.taskapp.viewmodels.reminder.durationModel.AddTaskDurationModel
import com.example.taskapp.viewmodels.reminder.durationModel.DurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.AddTaskFrequencyModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.AddTaskNotificationModel
import com.example.taskapp.viewmodels.reminder.notificationModel.NotificationModel
import dagger.Module
import dagger.Provides
import dagger.Reusable

inline fun <reified T: ViewModel> Fragment.viewModel(
    crossinline provider : () -> T
) = viewModels<T> {
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = provider() as T
    }
}

inline fun <reified T : ViewModel> Fragment.activityViewModel(
    crossinline provider: () -> T
) = activityViewModels<T> { object : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = provider() as T
} }

@Module
object ModelModule{

    @Reusable
    @Provides
    @JvmStatic
    fun provideSchedulersProvider() : SchedulerProvider = DefaultSchedulerProvider()

    @Provides
    @JvmStatic
    fun provideTaskDetailsModel() : TaskDetailsModel =  DefaultTaskDetailsModel()

    @Provides
    @JvmStatic
    fun provideNotificationModel() : NotificationModel = AddTaskNotificationModel()

    @Provides
    @JvmStatic
    fun provideDurationModel() : DurationModel = AddTaskDurationModel()

    @Provides
    @JvmStatic
    fun provideFrequencyModel() : FrequencyModel = AddTaskFrequencyModel()
}
