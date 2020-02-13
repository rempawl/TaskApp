package com.example.taskapp.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.taskapp.database.BaseDao
import com.example.taskapp.database.entities.Task
import com.example.taskapp.database.entities.TaskMinimal
import org.threeten.bp.LocalDate

@Dao
interface TaskDao : BaseDao<Task> {
    @Query("SELECT * FROM tasks")
    fun loadAllTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE taskID ==:taskID")
    fun loadTaskById(taskID:Long) : Task

    @Query("SELECT taskID, name FROM tasks")
    fun loadMinimalTasks() : List<TaskMinimal>

    @Query("DELETE FROM tasks WHERE taskID == :id")
    fun deleteByID(id: Long) : Int

    @Query("SELECT taskID,name FROM tasks WHERE updateDate = :date")
    fun loadMinTasksByUpdateDate(date: LocalDate ) : List<TaskMinimal>

    @Query("SELECT * FROM tasks WHERE updateDate = :date")
    fun loadTasksByUpdateDate(date: LocalDate ) : List<Task>



}