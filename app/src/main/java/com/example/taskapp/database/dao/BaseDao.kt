package com.example.taskapp.database.dao

import androidx.room.*

@Dao
interface BaseDao<T> {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item : T) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items : List<T>)

    @Update
    suspend fun updateItem(item: T) : Int

    @Update
    suspend fun updateItems(items : List<T>) : Int

    @Delete
    suspend fun deleteItem(item  : T)

    @Delete
    suspend fun deleteItems(items: List<T>)

}