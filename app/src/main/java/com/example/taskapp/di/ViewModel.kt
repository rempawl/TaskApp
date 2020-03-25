@file:Suppress("UNCHECKED_CAST")

package com.example.taskapp.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskapp.viewmodels.addTask.DefaultTaskDetailsModel
import com.example.taskapp.viewmodels.addTask.TaskDetailsModel
import dagger.Module
import dagger.Provides

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
object ViewModelModule{
    @Provides
    @JvmStatic
    fun provideTaskDetailsViewModel() : TaskDetailsModel =  DefaultTaskDetailsModel()
}
