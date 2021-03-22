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
            if (it.data.isEmpty()) return@let false
            val cl = it.data.first()?.javaClass
            check(cl is T) {
                "wrong data type $cl"
            }
            true
        } ?: false
    }

    inline fun <reified T> checkIfIsSuccessAnd(): Boolean {
        return this.takeIf { it.isSuccess() }?.let {
            it as Success<*>
            check(it.data is T) { "wrong type of data" }
            true
        } ?: false

    }


    companion object
}