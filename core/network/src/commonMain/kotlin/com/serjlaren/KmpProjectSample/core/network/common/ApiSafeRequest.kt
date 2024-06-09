package com.serjlaren.KmpProjectSample.core.network.common

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import kotlinx.serialization.SerializationException

internal suspend inline fun <reified T> HttpClient.safeGet(
    url: String,
    queryParams: Map<String, String>,
): ApiResponse<T, ApiErrorBody> {
    return try {
        val response = get {
            url(url)
            queryParams.forEach { queryParam ->
                parameter(queryParam.key, queryParam.value)
            }
        }
        ApiResponse.Success(response.body())
    } catch (e: Exception) {
        e.toApiError()
    }
}

internal suspend inline fun <reified T> HttpClient.safePost(
    url: String,
    requestBody: Any,
): ApiResponse<T, ApiErrorBody> {
    return try {
        val response = post {
            url(url)
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
        ApiResponse.Success(response.body())
    } catch (e: Exception) {
        e.toApiError()
    }
}

// TODO Function below just for test, will be removed later

internal suspend inline fun <reified T> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): ApiResponse<T, ApiErrorBody> {
    return try {
        val response = request(ApiHttpClient.BASE_URL) {
            url.protocol = URLProtocol.HTTPS
            headers { "X-Custom-Header" to "Hello" } // TODO headers just for example (maybe didn't work)
            accept(ContentType.Application.Json)
            block()
        }
        ApiResponse.Success(response.body())
    } catch (e: Exception) {
        e.toApiError()
    }
}

private suspend fun Exception.toApiError(): ApiResponse.Error<out ApiErrorBody> {
    return when (this) {
        is ClientRequestException -> {
            ApiResponse.Error.HttpError(
                this.response.status.value,
                this.errorBody()
            )
        }

        is ServerResponseException -> {
            ApiResponse.Error.HttpError(
                this.response.status.value,
                this.errorBody()
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

private suspend fun ResponseException.errorBody(): ApiErrorBody? =
    try {
        response.body<ApiErrorBody>()
    } catch (e: SerializationException) {
        null
    }