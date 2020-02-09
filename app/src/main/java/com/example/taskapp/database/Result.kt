package com.example.taskapp.database

import kotlin.Exception

sealed class Result<out R> {
    data class  Success <out T> (val items: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}