package com.example.taskapp.viewmodels

import android.view.View
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskapp.database.entities.task.DefaultTask
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.providers.SchedulerProvider
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import com.example.taskapp.viewmodels.reminder.durationModel.AddTaskDurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.NotificationModel
import com.example.taskapp.viewmodels.taskDetails.TaskDetailsModel
import io.reactivex.Single
import kotlinx.coroutines.launch
import javax.inject.Inject


class AddTaskViewModel @Inject constructor(
    taskDetailsModel: TaskDetailsModel,
    private val taskRepository: TaskRepositoryInterface,
    durationModel: AddTaskDurationModel,
    notificationModel: NotificationModel,
    frequencyModel: FrequencyModel,
    schedulerProvider: SchedulerProvider
) : ReminderViewModel(
    taskDetailsModel = taskDetailsModel,
    task = DefaultTask(name = ""),
    durationModel = durationModel,
    notificationModel = notificationModel,
    frequencyModel = frequencyModel,
    schedulerProvider = schedulerProvider
) {
    val onFocusTaskName: View.OnFocusChangeListener = View.OnFocusChangeListener { view, focused ->
        onFocusChange(view, focused, taskDetailsModel)
    }

    private val _isConfirmBtnClicked = MutableLiveData(false)
    override val isConfirmBtnClicked: LiveData<Boolean>
        get() = _isConfirmBtnClicked


    private fun onFocusChange(view: View?, focused: Boolean, taskDetailsModel: TaskDetailsModel) {
        val text = (view as EditText).text.toString()
        if (!focused && text.isNotEmpty()) {
            taskDetailsModel.isTaskNameValid(true)
        }
    }


    override fun onSaveTaskFinished() {
        _isConfirmBtnClicked.value = false
    }

    override  suspend fun addTask(task: DefaultTask) {
        _isConfirmBtnClicked.value = true

    taskRepository.saveTask(task)
    }


}


