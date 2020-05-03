package com.example.taskapp.database.dao

import androidx.lifecycle.LiveData
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
    fun getAllTasks(): List<DefaultTask>


    @Query("SELECT * FROM tasks WHERE taskID ==:taskID")
    fun getTaskById(taskID: Long): LiveData<DefaultTask>

    @Query("SELECT taskID, name,description FROM tasks")
    fun getMinimalTasks(): LiveData<List<TaskMinimal>>

    @Query("DELETE FROM tasks WHERE taskID == :id")
    fun deleteByID(id: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(item: DefaultTask): Single<Long>

    @Query("SELECT * FROM tasks WHERE realizationDate != :date")
    fun getTasksWithRealizationDateDifferentThanDate(date: LocalDate) : List<DefaultTask>


    @Query("SELECT taskID,name,description FROM tasks WHERE realizationDate = :date")
    fun getMinTasksByRealizationDate(date: LocalDate): LiveData<List<TaskMinimal>>


    @Query("SELECT * FROM tasks WHERE realizationDate = :date")
    fun getTasksByRealizationDate(date: LocalDate): LiveData<List<DefaultTask>>

    @Query("SELECT * FROm tasks WHERE realizationDate <= :date")
    fun getTaskWithRealizationDateUntilDate(date: LocalDate): List<DefaultTask>

}