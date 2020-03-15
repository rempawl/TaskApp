package com.example.taskapp.di

import android.content.Context
import com.example.taskapp.MainActivity
import com.example.taskapp.MyApp
import com.example.taskapp.fragments.MyTasksFragment
import com.example.taskapp.fragments.PickCustomNotificationDelayFragment
import com.example.taskapp.fragments.today.AddSpontaneousTaskDialogFragment
import com.example.taskapp.fragments.today.TodayFragment
import com.example.taskapp.viewmodels.AddReminderViewModel
import com.example.taskapp.viewmodels.EditTaskViewModel
import com.example.taskapp.viewmodels.TaskDetailsViewModel
import com.example.taskapp.viewmodels.addTask.AddTaskViewModel
import com.example.taskapp.workers.UpdateRemindersWorker
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
    modules = [AssistedInjectModule::class,
        AndroidInjectionModule::class,
        DataBaseModule::class,
        RepositoryModule::class,
    WorkerModule::class
    ]

)

@Singleton
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }


    fun inject(todayFragment: TodayFragment)
    fun inject(updateNotificationsAndTaskListWorker: UpdateRemindersWorker)
    fun inject(myTasksFragment: MyTasksFragment)
    fun inject(myApp: MyApp)
    fun inject(mainActivity: MainActivity)
    fun inject(addSpontaneousTaskDialogFragment: AddSpontaneousTaskDialogFragment)
    fun inject(pickCustomNotificationDelayFragment: PickCustomNotificationDelayFragment)

    val addReminderViewModelFactory: AddReminderViewModel.Factory
    val addTaskViewModel: AddTaskViewModel
    val editTaskViewModelFactory: EditTaskViewModel.Factory
    val taskDetailsViewModelFactory: TaskDetailsViewModel.Factory
}