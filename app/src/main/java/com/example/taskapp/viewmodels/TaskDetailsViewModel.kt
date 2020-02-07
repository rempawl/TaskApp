package com.example.taskapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.taskapp.database.entities.Task
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import javax.inject.Inject

class TaskDetailsViewModel  @AssistedInject constructor(@Assisted val task : Task
): ViewModel() {
    @AssistedInject.Factory
    interface Factory{
        fun create(task: Task) : TaskDetailsViewModel
    }
}
