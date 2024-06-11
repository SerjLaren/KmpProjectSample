package com.serjlaren.sharedumbrella.common

sealed class RemoteResult<out T> {

    class Success<T>(val data: T) : RemoteResult<T>()

    sealed class Error : RemoteResult<Nothing>() {

        class ServerError(val error: RemoteError) : Error()

        data object NetworkError : Error()

        data object SerializationError : Error()
    }
}

class RemoteError(
    val httpCode: Int,
    val serverCode: Int?,
    val message: String,
)