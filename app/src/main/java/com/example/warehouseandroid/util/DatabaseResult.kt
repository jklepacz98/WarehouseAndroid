package com.example.warehouseandroid.util

sealed class DatabaseResult<T : Any> {
    class Success<T : Any>(val data: T) : DatabaseResult<T>()
    class Error<T : Any>(val e: Throwable) : DatabaseResult<T>()
}