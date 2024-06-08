package com.serjlaren.KmpProjectSample.core.network.common

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import kotlinx.serialization.SerializationException

internal suspend inline fun <reified T> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): ApiResponse<T, ApiErrorBody> {
    return try {
        val response = request(DefaultHttpClient.BASE_URL) {
            url.protocol = URLProtocol.HTTPS
            headers { "X-Custom-Header" to "Hello" } // TODO headers just for example (maybe didn't work)
            accept(ContentType.Application.Json)
            block()
        }
        ApiResponse.Success(response.body())
    } catch (e: Exception) {
        when (e) {
            is ClientRequestException -> {
                ApiResponse.Error.HttpError(
                    e.response.status.value,
                    e.errorBody()
                )
            }

            is ServerResponseException -> {
                ApiResponse.Error.HttpError(
                    e.response.status.value,
                    e.errorBody()
                )
            }

            is SerializationException -> {
                ApiResponse.Error.SerializationError
            }

            else -> {
                ApiResponse.Error.NetworkError
            }
        }
    }
}

private suspend fun ResponseException.errorBody(): ApiErrorBody? =
    try {
        response.body<ApiErrorBody>()
    } catch (e: SerializationException) {
        null
    }