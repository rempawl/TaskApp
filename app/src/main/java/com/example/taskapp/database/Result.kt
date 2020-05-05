package com.example.taskapp.database

sealed class Result<out R> {
    data class  Success <out T> (val items: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
//    object Loading : Result<Any>()
}