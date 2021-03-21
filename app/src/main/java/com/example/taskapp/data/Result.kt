package com.example.taskapp.data

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
//    object Loading : Result<Any>()

    fun isError() = this is Error
    fun isSuccess() = this is Success

    inline fun <reified T> checkIfIsSuccessAndListOf(): Boolean {
        return takeIf { this.isSuccess() }?.let {
            it as Success
            check(it.data is List<*>)
            check(it.data.javaClass is T) { "wrong data type " }
            true
        } ?: false
    }


    companion object
}