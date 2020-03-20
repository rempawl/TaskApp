package com.example.taskapp.repos.task

import com.example.taskapp.database.entities.Task
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.repos.task.DefaultTasks.errorTask
import com.example.taskapp.repos.task.DefaultTasks.tasks
import io.reactivex.Single
import org.threeten.bp.LocalDate

class FakeTaskRepository : TaskRepositoryInterface {
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