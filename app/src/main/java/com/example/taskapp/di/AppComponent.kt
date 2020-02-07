package com.example.taskapp.di

import android.content.Context
import com.example.taskapp.viewmodels.MyTasksViewModel
import com.example.taskapp.viewmodels.TaskDetailsViewModel
import com.example.taskapp.viewmodels.TodayViewModel
import com.example.taskapp.viewmodels.addReminder.AddReminderViewModel
import com.example.taskapp.viewmodels.addTask.AddTaskViewModel
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [AssistedInjectModule::class,
        DataBaseModule::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }

    val todayViewModel : TodayViewModel
    val addReminderViewModelFactory: AddReminderViewModel.Factory
    val addTaskViewModel: AddTaskViewModel
    val myTaskViewModel: MyTasksViewModel
    val taskDetailsViewModelFactory: TaskDetailsViewModel.Factory
}