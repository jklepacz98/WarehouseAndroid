package com.example.warehouseandroid.util

suspend fun <T : Any> handleDatabase(
    execute: suspend () -> T
): DatabaseResult<T> {
    return try {
        val result = execute()
        DatabaseResult.Success(result)
    } catch (e: Exception) {
        DatabaseResult.Error(e)
    }
}