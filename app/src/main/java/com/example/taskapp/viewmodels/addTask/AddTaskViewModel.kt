package com.example.taskapp.viewmodels.addTask

import android.view.View
import android.widget.EditText
import androidx.lifecycle.ViewModel
import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.repos.task.TaskRepositoryInterface
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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

    fun getTask(): DefaultTask {
        return taskFields.createTask()
    }

    fun getTaskDetails(): TaskDetails {
        return taskFields.createTaskDetails()
    }

    suspend fun saveTask() {
//        viewModelScope.launch {
            val newTask = taskFields.createTask()

            compositeDisposable.add(
                addTask(newTask)
                    .subscribeOn(Schedulers.io())
                    .subscribe({}, { it.printStackTrace() })
            )
//        }
    }

    private suspend fun addTask(newTask: DefaultTask): Single<Long> {
        return taskRepository.saveTask(newTask)

    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()

    }

    companion object


}

