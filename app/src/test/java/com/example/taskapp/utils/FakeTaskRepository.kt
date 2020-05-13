package com.example.taskapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.taskapp.database.entities.task.DefaultTask
import com.example.taskapp.database.entities.task.TaskMinimal
import com.example.taskapp.database.entities.task.toTaskMinimal
import com.example.taskapp.repos.task.TaskRepositoryInterface
import io.reactivex.Single
import org.threeten.bp.LocalDate

class FakeTaskRepository : TaskRepositoryInterface {
    val tasks = DefaultTasks.tasks

    override suspend fun getTasks(): List<DefaultTask> = tasks.toList()

    override suspend fun deleteByID(id: Long): Int {
        val wasDeleted = tasks.removeIf { task : DefaultTask -> task.taskID == id }
        return if (wasDeleted) {
            1
        } else {
            0
        }
    }

    override suspend fun saveTask(task: DefaultTask)
            : Single<Long> {
        val predicate = { t: DefaultTask -> t.name == task.name || t.taskID == task.taskID }
        if (tasks.any(predicate)) {
            tasks.remove(tasks.first(predicate))
            tasks.add(task)
        }

        return Single.just(task.taskID)
    }

    override suspend fun getMinTasksByUpdateDate(date: LocalDate): List<TaskMinimal> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTasksByUpdateDate(date: LocalDate): List<DefaultTask> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTaskByID(id: Long): DefaultTask {
        val predicate = { task: DefaultTask -> task.taskID == id }
        return if (tasks.any(predicate)) {
            tasks.first(predicate)
        } else {
            DefaultTasks.errorTask
        }
    }


    override suspend fun getMinimalTasks(): LiveData<List<TaskMinimal>> {
        return liveData {
            emit(tasks.map { task -> task.toTaskMinimal() })
        }
    }

    override suspend fun getTasksUntilDate(date: LocalDate): List<DefaultTask> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateTask(task: DefaultTask): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateTasks(tasks: List<DefaultTask>): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getTodayMinTasks(): LiveData<List<TaskMinimal>> {
        return MutableLiveData(DefaultTasks.minimalTasks)

    }

    override suspend fun getNotTodayTasks(): List<DefaultTask> {
        TODO("Not yet implemented")
    }
}
