package com.vivek.swiggy.data.models

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val error: String) : ApiResponse<Nothing>()
    data object Loading: ApiResponse<Nothing>()
    data object Idle: ApiResponse<Nothing>()

}