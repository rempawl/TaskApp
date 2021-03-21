package com.example.taskapp.data

sealed class Result<out R> {
    data class Success<out T>(val items: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
//    object Loading : Result<Any>()

    fun isError() = this is Error
    fun isSuccess() = this is Success
}