package com.example.taskapp.di

import android.app.Application
import android.content.Context
import com.example.taskapp.MainActivity
import com.example.taskapp.MyApp
import com.example.taskapp.fragments.MyTasksFragment
import com.example.taskapp.fragments.TodayFragment
import com.example.taskapp.viewmodels.AddReminderViewModel
import com.example.taskapp.viewmodels.EditTaskViewModel
import com.example.taskapp.viewmodels.TaskDetailsViewModel
import com.example.taskapp.viewmodels.addTask.AddTaskViewModel
import com.example.taskapp.workers.UpdateNotificationsAndTaskListWorker
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [AssistedInjectModule::class,
        DataBaseModule::class,
        RepositoryModule::class,
    WorkerModule::class
    ]
)
@Singleton
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context, @BindsInstance application: Application): AppComponent
    }

    fun inject(todayFragment: TodayFragment)
    fun inject(updateNotificationsAndTaskListWorker: UpdateNotificationsAndTaskListWorker)
    fun inject(myTasksFragment: MyTasksFragment)
    fun inject(myApp: MyApp)
    fun inject(mainActivity: MainActivity)

    val addReminderViewModelFactory: AddReminderViewModel.Factory
    val addTaskViewModel: AddTaskViewModel
    val editTaskViewModelFactory: EditTaskViewModel.Factory
    val taskDetailsViewModelFactory: TaskDetailsViewModel.Factory
}