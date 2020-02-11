package com.example.taskapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.taskapp.viewmodels.addReminder.DurationModel
import com.example.taskapp.viewmodels.addReminder.FrequencyModel
import com.example.taskapp.viewmodels.addTask.TaskFields
import javax.inject.Inject

class EditTaskViewModel @Inject constructor( val taskFields: TaskFields,
                                            val durationModel: DurationModel,
                                             val frequencyModel: FrequencyModel
                                            ) : ViewModel()
