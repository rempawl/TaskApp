package com.example.taskapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.taskapp.repos.task.TaskRepository
import org.threeten.bp.LocalDate
import javax.inject.Inject

class TodayViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {

    companion object {
        val TODAY: LocalDate = LocalDate.now()
    }

    val tasks = liveData {
        val data = taskRepository.getMinTasksByUpdateDate(TODAY)
        emit(data)
    }

    init{
//        updateTasks()
    }
//    private fun updateTasks() {
//        viewModelScope.launch {
//             taskRepository.getTasksByUpdateDate(TODAY).forEach { task: Task ->
//                val freqState = task.reminder!!.frequency.convertToFrequencyState()
//                val newDate = freqState.calculateUpdateDate(TODAY,false)
//                val newReminder = task.reminder.copy(updateDate = newDate)
//                Log.d(MainActivity.TAG,task.reminder.toString())
//                Log.d(MainActivity.TAG,newReminder.toString())
//                taskRepository.updateTask(task.copy(reminder = newReminder))
//            }
//        }
//    }

}
