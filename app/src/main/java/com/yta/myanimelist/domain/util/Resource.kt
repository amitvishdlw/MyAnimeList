package com.yta.myanimelist.domain.util

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()

    class Error<T>(
        val statusCode: StatusCode,
        val message: String? = null,
        val data: T? = null
    ) : Resource<T>()

    val isSuccess: Boolean
        get() = this is Success

    val isError: Boolean
        get() = this is Error

    override fun toString(): String = when (this) {
        is Success -> "Resource.Success(data = $data)"
        is Error -> "Resource.Error(statusCode = $statusCode)"
    }

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> errorGeneric() = Error<T>(statusCode = GenericStatusCode.Generic)
    }
}

interface StatusCode

enum class GenericStatusCode : StatusCode {
    Generic,
    NoInternet,
    ApiFailed
}