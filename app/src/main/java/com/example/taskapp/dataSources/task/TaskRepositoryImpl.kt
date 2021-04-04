package com.example.taskapp.dataSources.task

import com.example.taskapp.MyApp
import com.example.taskapp.data.Result
import com.example.taskapp.data.task.Task
import com.example.taskapp.data.task.TaskMinimal
import com.example.taskapp.database.dao.TaskDao
import com.example.taskapp.database.entities.task.DbTask
import com.example.taskapp.utils.DateUtils.TODAY
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDate
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
@Reusable
class TaskRepositoryImpl @Inject constructor(private val taskLocalDataSource: TaskDao) :
    TaskRepository {

    @Suppress("UNCHECKED_CAST")

    private inline fun <T, E> getData(
        getter: () -> Flow<T>,
        crossinline mapper: (T) -> E
    ): Flow<Result<*>> {
        return getter().catch { e -> Result.Error(e) }
            .map { Result.Success(mapper(it)) }
    }

    private inline fun <T, E> getListData(
        getter: () -> Flow<List<T>>,
        crossinline mapper: (T) -> E
    ): Flow<Result<*>> {
        return getter().catch { e -> Result.Error(e) }
            .map { it.map { x -> mapper(x) } }
            .map { Result.Success(it) }
    }

    override suspend fun getTasks(): Flow<Result<*>> {
        return getListData({ taskLocalDataSource.getAllTasks() }, { Task.from(it) })
    }

    override suspend fun deleteByID(id: Long) = taskLocalDataSource.deleteByID(id)

    override suspend fun saveTask(task: Task) {
        val item = DbTask.from(task)
        taskLocalDataSource.insertItem(item)
    }

    override suspend fun getNotTodayTasks(): Flow<Result<*>> {
        return getListData({
            taskLocalDataSource.getTasksWithRealizationDateDifferentThanDate(TODAY)
        }, {
            Task.from(it)
        })
    }

    override suspend fun getMinTasksByUpdateDate(date: LocalDate): Flow<Result<*>> {
        return getListData({ taskLocalDataSource.getMinTasksByRealizationDate(date) },
            { TaskMinimal.from(it) })
    }

    override suspend fun getTodayMinTasks(): Flow<Result<*>> {
        return getListData({ taskLocalDataSource.getMinTasksByRealizationDate(TODAY) },
            { TaskMinimal.from(it) })
    }


    override suspend fun getTasksByUpdateDate(date: LocalDate): Flow<Result<*>> {
        return getListData(
            { taskLocalDataSource.getTasksByRealizationDate(date) },
            { Task.from(it) })
    }

    override suspend fun getTaskByID(id: Long): Flow<Result<*>> {
        return getData({ taskLocalDataSource.getTaskById(id) }, { Task.from(it) })

    }


    override suspend fun getMinimalTasks(): Flow<Result<*>> =
        getListData({ taskLocalDataSource.getMinimalTasks() }, { TaskMinimal.from(it) })

    override suspend fun getTasksUntilDate(date: LocalDate): Flow<Result<*>> {
        return getListData(
            { taskLocalDataSource.getTaskWithRealizationDateUntilDate(date) },
            { Task.from(it) })
    }

    override suspend fun updateTask(task: Task): Int {
        val item = DbTask.from(task)
        return taskLocalDataSource.updateItem(item)

    }

    override suspend fun updateTasks(tasks: List<Task>): Int {
        val items = tasks.map { DbTask.from(it) }
        return taskLocalDataSource.updateItems(items)

    }
}

