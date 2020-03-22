package com.example.taskapp.viewmodels.addTask

import android.view.View
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskapp.database.entities.Task
import com.example.taskapp.repos.task.TaskRepositoryInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddTaskViewModel @Inject constructor(
    val taskFields: TaskDetailsModel,
    private val taskRepository: TaskRepositoryInterface
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val onFocusTaskName: View.OnFocusChangeListener = View.OnFocusChangeListener { view, focused ->
        val text = (view as EditText).text.toString()
        if (!focused && text.isNotEmpty()) {
            taskFields.isTaskNameValid(true)
        }
    }


    val onFocusTaskDescription = View.OnFocusChangeListener { view, focused ->
        val text = (view as EditText).text.toString()
        if (!focused && text.isBlank()) {
            taskFields.validateTaskDescription()
        }
    }

    fun getTask() : Task {
        return taskFields.createTask()
    }

    fun getTaskDetails(): TaskDetails {
        return taskFields.createTaskDetails()
    }

    fun saveTask() {
        viewModelScope.launch {
            val newTask = taskFields.createTask()
            compositeDisposable.add(
                taskRepository.saveTask(newTask)
                    .subscribeOn(Schedulers.io())
                    .subscribe({}, { it.printStackTrace() })
            )
        }
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()

    }

    companion object


}

