package com.serjlaren.KmpProjectSample.core.network

import com.serjlaren.KmpProjectSample.core.network.common.DefaultHttpClient
import com.serjlaren.KmpProjectSample.core.network.common.MyApiResponse
import com.serjlaren.KmpProjectSample.core.network.common.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

class CommentsApi {

    private val httpClient: HttpClient = DefaultHttpClient.client

    suspend fun getComments(): MyApiResponse<List<CommentDto>> {
        return httpClient.safeRequest<List<CommentDto>> {
            method = HttpMethod.Get
            url("comments")
            url.parameters.append("token", "abc123") // TODO query params just for example
            build()
        }
    }

    fun observeComments(): Flow<MyApiResponse<List<CommentDto>>> {
        return flow {
            emit(
                httpClient.safeRequest<List<CommentDto>> {
                    method = HttpMethod.Get
                    url("comments")
                    url.parameters.append("token", "abc123") // TODO query params just for example
                    build()
                }
            )
        }
    }

    // TODO This function is just for test, in future need to delete it
    @OptIn(InternalSerializationApi::class)
    suspend fun <T : Any> getCustom(
        url: String,
        responseClass: KClass<T>,
        query: List<Pair<String, String>> = listOf(),
    ): T {
        val response = httpClient.get(url) {
            if (query.isNotEmpty()) {
                url {
                    query.forEach { parameters.append(it.first, it.second) }
                }
            }
        }

        @Suppress("JSON_FORMAT_REDUNDANT")
        return Json {
            ignoreUnknownKeys = true
            useAlternativeNames = false
            prettyPrint = true
            isLenient = true
        }.decodeFromString(responseClass.serializer(), response.bodyAsText())
    }
}