package com.example.taskapp.database.dao

import  androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.taskapp.database.BaseDao
import com.example.taskapp.database.entities.Task

@Dao
interface TaskDao : BaseDao<Task> {
    @Query("SELECT * FROM tasks")
    fun loadAllTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE taskID ==:taskID")
    fun loadTaskById(taskID:Long) : Task


//    @Transaction
//        @Query("SELECT * FROM tasks ")
//    fun loadTasksWithReminders()  : List<TaskWithReminder>

//    @Transaction
//    @Query("SELECT * FROM tasks WHERE taskID = :taskID")
//    fun loadTaskWithRemindersById(taskID: Long) : TaskWithReminder

}