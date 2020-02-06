package com.example.taskapp.di

import android.content.Context
import androidx.room.Room
import com.example.taskapp.database.AppDataBase
import com.example.taskapp.viewmodels.MyTasksViewModel
import com.example.taskapp.viewmodels.addReminder.AddReminderViewModel
import com.example.taskapp.viewmodels.addTask.AddTaskViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import javax.inject.Inject

@Component(
    modules = [AssistedInjectModule::class,
    DataBaseModule::class
        ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context
                   ): AppComponent
    }

    val addReminderViewModelFactory: AddReminderViewModel.Factory
    val addTaskViewModel: AddTaskViewModel
    val myTaskViewModel: MyTasksViewModel

}