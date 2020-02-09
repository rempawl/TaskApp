package com.example.taskapp.database

import androidx.room.*

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item : T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items : List<T>)

    @Delete
    fun deleteItem(item  : T)

    @Delete
    fun deleteItems(items: List<T>)

}