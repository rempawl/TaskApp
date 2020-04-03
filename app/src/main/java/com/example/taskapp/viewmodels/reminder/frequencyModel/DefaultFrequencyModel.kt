package com.example.taskapp.viewmodels.reminder.frequencyModel

import com.example.taskapp.database.entities.Frequency
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class DefaultFrequencyModel @AssistedInject constructor(@Assisted frequency: Frequency?):
    FrequencyModel(frequency){

    @AssistedInject.Factory
    interface Factory {
        fun create(frequency: Frequency? = null): FrequencyModel
    }

}