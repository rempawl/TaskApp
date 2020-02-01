package com.example.taskapp.di

import   android.content.Context
import com.example.taskapp.viewmodels.AddReminderViewModel
import com.example.taskapp.viewmodels.addTask.AddTaskViewModel
import com.example.taskapp.viewmodels.MyTasksViewModel
import dagger.BindsInstance
import dagger.Component

@Component(modules=[AssistedInjectModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : AppComponent
    }

    val addReminderViewModelFactory: AddReminderViewModel.Factory
    val addTaskViewModel : AddTaskViewModel
    val myTaskViewModel : MyTasksViewModel

}