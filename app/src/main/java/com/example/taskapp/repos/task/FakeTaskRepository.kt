package com.example.taskapp.repos.task

import com.example.taskapp.MyApp.Companion.TODAY
import com.example.taskapp.database.entities.NotificationTime
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.database.entities.Task
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.utils.reminder.ReminderDurationState
import com.example.taskapp.utils.reminder.ReminderFrequencyState
import io.reactivex.Single
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

class FakeTaskRepository : TaskRepositoryInterface {
    companion object {
        private val frequencyMondayTuesday =
            ReminderFrequencyState.WeekDays(setOf(DayOfWeek.TUESDAY.value, DayOfWeek.MONDAY.value))
        private val frequency2days = ReminderFrequencyState.Daily(2)

        private val duration10Days = ReminderDurationState.DaysDuration(10)
        private val durationEndDate = ReminderDurationState.EndDate(
            LocalDate.ofEpochDay(TODAY.toEpochDay() + 20)
        )

        private val notificationTimeTwelve = NotificationTime(12, 0, true)

        private val firstReminder = Reminder(
            begDate = TODAY,
            frequency = frequencyMondayTuesday.convertToFrequency(),
            duration = duration10Days.convertToDuration(),
            expirationDate = duration10Days.calculateEndDate(begDate = TODAY),
            realizationDate = frequencyMondayTuesday.calculateRealizationDate(TODAY, true),
            notificationTime = notificationTimeTwelve
        )

        private val secondReminder = Reminder(
            begDate = TODAY,
            frequency = frequency2days.convertToFrequency(),
            duration = durationEndDate.convertToDuration(),
            expirationDate = durationEndDate.calculateEndDate(TODAY),
            realizationDate = frequency2days.calculateRealizationDate(TODAY, true),
            notificationTime = notificationTimeTwelve
        )

        val tasks = mutableListOf<Task>(
            Task(taskID = 0, name = "first", description = "no reminder"),
            Task(
                taskID = 1,
                name = "second",
                description = "with first reminder",
                reminder = firstReminder
            ),
            Task(
                taskID = 2,
                name = "third",
                description = "with second reminder",
                reminder = secondReminder
            ),
            Task(
                taskID = 3,
                name = "fourth",
                description = "with first reminder",
                reminder = firstReminder
            ),
            Task(
                taskID = 4,
                name = "fifth",
                description = "with second reminder",
                reminder = secondReminder
            )
        )

        private val errorTask = Task(name = "error")
    }
    override suspend fun getTasks(): List<Task> = tasks.toList()

    override suspend fun deleteByID(id: Long): Int {
        val wasDeleted = tasks.removeIf { task -> task.taskID == id }
        return if (wasDeleted) {
            1
        } else {
            0
        }
    }

    override suspend fun saveTask(task: Task)
            : Single<Long>
    {
        val predicate = { t: Task -> t.name == task.name || t.taskID == task.taskID }
        if (tasks.any(predicate)) {
            tasks.remove(tasks.first(predicate))
            tasks.add(task)
        }
        TODO()
    }

    override suspend fun getMinTasksByUpdateDate(date: LocalDate): List<TaskMinimal> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTasksByUpdateDate(date: LocalDate): List<Task> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTaskByID(id: Long): Task {
        val predicate = { task: Task -> task.taskID == id }
        return if (tasks.any(predicate)) {
            tasks.first(predicate)
        } else {
            errorTask
        }
    }


    override suspend fun getMinimalTasks(): List<TaskMinimal> {
        return tasks.map { task ->
            TaskMinimal(
                taskID = task.taskID,
                name = task.name,
                description = task.description
            )
        }
    }

    override suspend fun getTasksUntilDate(date: LocalDate): List<Task> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateTask(task: Task): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateTasks(tasks: List<Task>): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getTodayMinTasks(): List<TaskMinimal> {
        TODO("Not yet implemented")
    }

    override suspend fun getNotTodayTasks(): List<Task> {
        TODO("Not yet implemented")
    }
}