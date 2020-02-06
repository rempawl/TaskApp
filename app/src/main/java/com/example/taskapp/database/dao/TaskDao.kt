package com.example.taskapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.taskapp.database.BaseDao
import com.example.taskapp.database.entities.Task

@Dao
interface TaskDao : BaseDao<Task> {
    @Query("SELECT * FROM tasks")
    fun loadAllTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE taskID ==:taskID")
    fun loadTaskById(taskID:Long) : Task


}