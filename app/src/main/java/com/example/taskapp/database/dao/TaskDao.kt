package com.example.taskapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskapp.database.BaseDao
import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.database.entities.TaskMinimal
import io.reactivex.Single
import org.threeten.bp.LocalDate

@Dao
interface TaskDao : BaseDao<DefaultTask> {



    @Query("SELECT * FROM tasks")
    fun loadAllTasks(): List<DefaultTask>


    @Query("SELECT * FROM tasks WHERE taskID ==:taskID")
    fun loadTaskById(taskID: Long): DefaultTask

    @Query("SELECT taskID, name,description FROM tasks")
    fun loadMinimalTasks(): List<TaskMinimal>

    @Query("DELETE FROM tasks WHERE taskID == :id")
    fun deleteByID(id: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(item: DefaultTask): Single<Long>

    @Query("SELECT * FROM tasks WHERE realizationDate != :date")
    fun loadTasksWithRealizationDateDifferentThanDate(date: LocalDate) : List<DefaultTask>


    @Query("SELECT taskID,name,description FROM tasks WHERE realizationDate = :date")
    fun loadMinTasksByRealizationDate(date: LocalDate): List<TaskMinimal>


    @Query("SELECT * FROM tasks WHERE realizationDate = :date")
    fun loadTasksByRealizationDate(date: LocalDate): List<DefaultTask>

    @Query("SELECT * FROm tasks WHERE realizationDate <= :date")
    fun loadTaskWithRealizationDateUntilDate(date: LocalDate): List<DefaultTask>

}