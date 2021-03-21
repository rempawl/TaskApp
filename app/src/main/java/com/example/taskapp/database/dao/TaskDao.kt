package com.example.taskapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskapp.database.entities.task.DbTask
import com.example.taskapp.database.entities.task.DbTaskMinimal
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate
@Dao
interface TaskDao : BaseDao<DbTask> {



    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<DbTask>>


    @Query("SELECT * FROM tasks WHERE taskID ==:taskID")
    fun getTaskById(taskID: Long): Flow<DbTask>

    @Query("SELECT taskID, name,description FROM tasks")
    fun getMinimalTasks(): Flow<List<DbTaskMinimal>>

    @Query("DELETE FROM tasks WHERE taskID == :id")
    suspend fun deleteByID(id: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(item: DbTask): Long

    @Query("SELECT * FROM tasks WHERE realizationDate != :date")
    fun getTasksWithRealizationDateDifferentThanDate(date: LocalDate) : Flow<List<DbTask>>


    @Query("SELECT taskID,name,description FROM tasks WHERE realizationDate = :date")
    fun getMinTasksByRealizationDate(date: LocalDate): Flow<List<DbTaskMinimal>>

    @Query("SELECT * FROM tasks WHERE realizationDate = :date")
    fun getTasksByRealizationDate(date: LocalDate): Flow<List<DbTask>>

    @Query("SELECT * FROm tasks WHERE realizationDate <= :date")
    fun getTaskWithRealizationDateUntilDate(date: LocalDate): Flow<List<DbTask>>

}