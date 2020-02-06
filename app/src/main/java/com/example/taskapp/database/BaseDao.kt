package com.example.taskapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface BaseDao<T> {

    @Insert
    fun insertItem(item : T)

    @Insert
    fun insertItems(items : List<T>)

    @Delete
    fun deleteItem(item  : T)

    @Delete
    fun deleteItems(items: List<T>)

}