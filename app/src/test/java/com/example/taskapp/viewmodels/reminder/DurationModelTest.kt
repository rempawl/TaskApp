package com.example.taskapp.viewmodels.reminder

import com.example.taskapp.loadTimeZone
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.threeten.bp.LocalDate

internal class DurationModelTest {
    init {
        loadTimeZone()
    }
    private lateinit var model: DurationModel

    companion object {
        private val TODAY = LocalDate.now()
    }

    @Nested
    @DisplayName("When initial Duration is null and begDate is today")
    inner class InitWithNullDuration {
        @BeforeEach
        fun setUp() {
            model = DurationModel(null, TODAY)
        }

    }
}