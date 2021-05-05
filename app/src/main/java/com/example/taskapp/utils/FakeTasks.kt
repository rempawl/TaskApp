package com.example.taskapp.utils

import com.example.taskapp.data.reminder.NotificationTime
import com.example.taskapp.data.reminder.Reminder
import com.example.taskapp.data.task.Task
import com.example.taskapp.database.entities.task.DbTask
import com.example.taskapp.utils.DateUtils.TODAY
import com.example.taskapp.viewmodels.reminder.ReminderDurationState
import com.example.taskapp.viewmodels.reminder.ReminderFrequencyState
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

object FakeTasks {
    private val frequencyMondayTuesday =
        ReminderFrequencyState.WeekDays(setOf(DayOfWeek.TUESDAY.value, DayOfWeek.MONDAY.value))
    private val frequency2days = ReminderFrequencyState.Daily(2)

    private val duration10Days = ReminderDurationState.DaysDuration(10)
    private val durationEndDate = ReminderDurationState.EndDate(
        LocalDate.ofEpochDay(TODAY.toEpochDay() + 20)
    )

    private val notificationTimeTwelve =
        NotificationTime(
            12,
            0,
            true
        )

    private val firstReminder =
        Reminder(
            begDate = TODAY,
            frequency = frequencyMondayTuesday.convertToFrequency(),
            duration = duration10Days.convertToDuration(),
            expirationDate = duration10Days.calculateEndDate(begDate = TODAY),
            realizationDate = frequencyMondayTuesday.calculateRealizationDate(TODAY, true),
            notificationTime = notificationTimeTwelve
        )

    private val secondReminder =
        Reminder(
            begDate = TODAY,
            frequency = frequency2days.convertToFrequency(),
            duration = durationEndDate.convertToDuration(),
            expirationDate = durationEndDate.calculateEndDate(TODAY),
            realizationDate = frequency2days.calculateRealizationDate(TODAY, true),
            notificationTime = notificationTimeTwelve
        )

    val dbTasks = mutableListOf<DbTask>(
        DbTask(
            taskID = 1,
            name = "first",
            description = "no reminder"
        ),
        DbTask(
            taskID = 2,
            name = "second",
            description = "with first reminder",
            reminder = firstReminder
        ),
        DbTask(
            taskID = 3,
            name = "third",
            description = "with second reminder",
            reminder = secondReminder
        ),
        DbTask(
            taskID = 4,
            name = "fourth",
            description = "with first reminder",
            reminder = firstReminder
        ),
        DbTask(
            taskID = 5,
            name = "fifth",
            description = "with second reminder",
            reminder = secondReminder
        )
    )


    val dbMinimalTasks = dbTasks.map { task -> task.toTaskMinimal() }
    val tasks = dbTasks.map { Task.from(it) }
    val minTasks = tasks.map{it.toTaskMinimal()}

    val errorTask = DbTask(
        taskID = -1,
        name = "error"
    )

}