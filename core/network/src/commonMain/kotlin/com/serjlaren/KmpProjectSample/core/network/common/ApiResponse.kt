package com.serjlaren.KmpProjectSample.core.network.common

typealias MyApiResponse<T> = ApiResponse<T, ApiErrorBody>

sealed class ApiResponse<out T, out E> {

    class Success<T>(val body: T) : ApiResponse<T, Nothing>()

    sealed class Error<E> : ApiResponse<Nothing, E>() {

        class HttpError<E>(val code: Int, val errorBody: E?) : Error<E>()

        data object NetworkError : Error<Nothing>()

        data object SerializationError : Error<Nothing>()
    }
}