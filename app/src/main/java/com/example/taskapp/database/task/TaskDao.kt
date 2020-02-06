package com.example.taskapp.database.task

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.taskapp.database.BaseDao

@Dao
interface TaskDao : BaseDao<Task> {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE taskID ==:taskID")
    fun getTaskById(taskID:Long) : LiveData<Task>


}