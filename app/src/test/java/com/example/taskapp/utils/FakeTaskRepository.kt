package com.example.taskapp.utils

import com.example.taskapp.data.Result
import com.example.taskapp.data.task.Task
import com.example.taskapp.dataSources.task.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.threeten.bp.LocalDate

class FakeTaskRepository : TaskRepository {

    private val tasks = FakeTasks.tasks.toMutableList()

    private fun <T> flowBuilder(getter: () -> T): Flow<T> = flow {
        emit(getter())
    }

    override suspend fun getTasks(): Flow<Result<*>> = flowBuilder {
        Result.Success(tasks)
    }


    override suspend fun deleteByID(id: Long): Int {
        val wasDeleted = tasks.removeIf { task: Task -> task.taskID == id }
        return if (wasDeleted) {
            1
        } else {
            0
        }
    }

    override suspend fun saveTask(task: Task) {
        val predicate = { t: Task -> t.name == task.name || t.taskID == task.taskID }
        if (tasks.any(predicate)) {
            tasks.remove(tasks.first(predicate))
            tasks.add(task)
        }
    }

    override suspend fun getMinTasksByUpdateDate(date: LocalDate): Flow<Result<*>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTasksByUpdateDate(date: LocalDate): Flow<Result<*>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTaskByID(id: Long): Flow<Result<*>> {
        val predicate = { task: Task -> task.taskID == id }
        return flowBuilder {
            if (tasks.any(predicate)) Result.Success(tasks.first(predicate))
            else Result.Error(Exception("task not found"))
        }
    }


    override suspend fun getMinimalTasks(): Flow<Result<*>> {
        return flowBuilder {
            Result.Success(tasks.map { task -> task.toTaskMinimal() })
        }
    }

    override suspend fun getTasksUntilDate(date: LocalDate): Flow<Result<*>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateTask(task: Task): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateTasks(tasks: List<Task>): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getTodayMinTasks(): Flow<Result<*>> {
        TODO()
    }

    override suspend fun getNotTodayTasks(): Flow<Result<*>> {
        TODO("Not yet implemented")
    }
}
