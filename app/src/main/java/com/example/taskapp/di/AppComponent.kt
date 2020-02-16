package com.example.taskapp.di

import android.content.Context
import com.example.taskapp.fragments.MyTasksFragment
import com.example.taskapp.fragments.TodayFragment
import com.example.taskapp.viewmodels.EditTaskViewModel
import com.example.taskapp.viewmodels.TaskDetailsViewModel
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

    fun inject(todayFragment: TodayFragment)

    fun inject(myTasksFragment : MyTasksFragment)

    //    val todayViewModel : TodayViewModel
    val addReminderViewModelFactory: AddReminderViewModel.Factory
    val addTaskViewModel: AddTaskViewModel
    //    val myTaskViewModel: MyTasksViewModel
    val editTaskViewModelFactory : EditTaskViewModel.Factory
    val taskDetailsViewModelFactory: TaskDetailsViewModel.Factory
}