package com.example.taskapp.viewmodels.addTask

import android.view.View
import android.widget.EditText
import androidx.lifecycle.ViewModel
import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.repos.task.TaskRepositoryInterface
import com.example.taskapp.utils.SchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


abstract class AddTaskViewModel : ViewModel(){
    abstract val onFocusTaskName: View.OnFocusChangeListener
    abstract val onFocusTaskDescription : View.OnFocusChangeListener

    abstract fun getTask(): DefaultTask
    abstract suspend fun saveTask()


}

class DefaultAddTaskViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    val taskFields: TaskDetailsModel,
    private val taskRepository: TaskRepositoryInterface
) : AddTaskViewModel() {

    private val compositeDisposable = CompositeDisposable()

    override val onFocusTaskName: View.OnFocusChangeListener = View.OnFocusChangeListener { view, focused ->
        val text = (view as EditText).text.toString()
        if (!focused && text.isNotEmpty()) {
            taskFields.isTaskNameValid(true)
        }
    }


    override val onFocusTaskDescription = View.OnFocusChangeListener { view, focused ->
        val text = (view as EditText).text.toString()
        if (!focused && text.isBlank()) {
            taskFields.validateTaskDescription()
        }
    }

    override fun getTask(): DefaultTask {
        return taskFields.createTask()
    }


    override suspend fun saveTask() {
            val newTask = taskFields.createTask()

            compositeDisposable.add(
                addTask(newTask)
                    .subscribeOn(schedulerProvider.getIoScheduler())
                    .observeOn(schedulerProvider.getUiScheduler())
                    .subscribe({}, { it.printStackTrace() })
            )
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

