package com.example.taskapp.repos.task

import com.example.taskapp.database.Result
import com.example.taskapp.database.entities.Task
import com.example.taskapp.database.entities.TaskMinimal
import io.reactivex.Single
import org.threeten.bp.LocalDate

class FakeTaskDataSource : TaskDataSource {
    override suspend fun saveTask(task: Task)
            : Single<Long>
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getMinTasksByUpdateDate(date: LocalDate): Result<List<TaskMinimal>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTasksUntilDate(date: LocalDate): Result<List<Task>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTasksByUpdateDate(date: LocalDate): Result<List<Task>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteTask(id: Long): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTasks(): Result<List<Task>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getMinimalTasks(): Result<List<TaskMinimal>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTaskById(id: Long): Result<Task> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateTask(task: Task): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateTasks(tasks: List<Task>): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getNotTodayTasks(): Result<List<Task>> {
        TODO("Not yet implemented")
    }
}