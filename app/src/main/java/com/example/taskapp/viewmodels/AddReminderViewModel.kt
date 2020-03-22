package com.example.taskapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskapp.MyApp.Companion.TODAY
import com.example.taskapp.database.entities.Task
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import com.example.taskapp.viewmodels.reminder.durationModel.DefaultDurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.DefaultFrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.DefaultNotificationModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalTime


class AddReminderViewModel @AssistedInject constructor(
    @Assisted task: Task,
    taskRepository: TaskRepositoryInterface,
    defaultDurationModelFactory: DefaultDurationModel.Factory,
    defaultNotificationModelFactory: DefaultNotificationModel.Factory,
    frequencyModelFactory: DefaultFrequencyModel.Factory
//    private val streakLocalDataSource: StreakDataSource,
) : ReminderViewModel(
    task, taskRepository, defaultDurationModelFactory,
    defaultNotificationModelFactory,
    frequencyModelFactory
) {

    @AssistedInject.Factory
    interface Factory {
        fun create(task: Task): AddReminderViewModel
    }
    /**
     * when _addedTask is not null then  notification alarm should be set
     */
    private val _addedTask = MutableLiveData<Task>(null)
    val addedTask: LiveData<Task>
        get() = _addedTask

    //todo save task
    fun saveTaskWithReminder() {
        viewModelScope.launch {
            val reminder = createReminder()
            val task = createTask(reminder)
            val isRealizationToday = reminder.realizationDate.isEqual(TODAY)

            compositeDisposable.add(
                addTask(task)
                    .subscribeOn(Schedulers.io())
                    .subscribe({ taskID ->
//                        saveStreak(taskID)
                    },
                        { e -> e.printStackTrace() }
                    )
            )
            if (isRealizationToday
                && reminder.notificationTime.convertToLocalTime().isAfter(LocalTime.now())
            ) {
                _addedTask.value = task
            }


        }
    }



}


