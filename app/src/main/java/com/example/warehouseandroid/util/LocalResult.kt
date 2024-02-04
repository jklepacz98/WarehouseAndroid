package com.example.warehouseandroid.util

sealed class LocalResult<T : Any> {
    class Success<T : Any>(val data: T) : LocalResult<T>()
    class Error<T : Any>(val e: Throwable) : LocalResult<T>()
}