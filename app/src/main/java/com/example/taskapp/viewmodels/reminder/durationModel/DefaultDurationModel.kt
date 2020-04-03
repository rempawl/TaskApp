package com.example.taskapp.viewmodels.reminder.durationModel

import com.example.taskapp.database.entities.Duration
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import org.threeten.bp.LocalDate

class DefaultDurationModel @AssistedInject constructor(
    @Assisted duration: Duration?,
    @Assisted begDate: LocalDate
) :
    DurationModel(duration, begDate) {

    @AssistedInject.Factory
    interface Factory {
        fun create(duration: Duration? = null, begDate: LocalDate = LocalDate.now()): DurationModel
    }
}
