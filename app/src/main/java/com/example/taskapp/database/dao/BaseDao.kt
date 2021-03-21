package com.example.taskapp.database.dao

import androidx.room.*

@Dao
interface BaseDao<T> {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item : T) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items : List<T>)

    @Update
    fun updateItem(item: T) : Int

    @Update
    fun updateItems(items : List<T>) : Int

    @Delete
    fun deleteItem(item  : T)

    @Delete
    fun deleteItems(items: List<T>)

}