package com.serjlaren.KmpProjectSample.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

class CommentsApi {

    private val json = Json {
        ignoreUnknownKeys = true
        useAlternativeNames = false
        prettyPrint = true
        isLenient = true
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(json)
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    suspend fun getComments(): List<CommentDto> {
        return httpClient.get("https://jsonplaceholder.org/comments").body()
    }

    fun observeComments(): Flow<List<CommentDto>> {
        return flow {
            emit(httpClient.get("https://jsonplaceholder.org/comments").body())
        }
    }

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

        return json.decodeFromString(responseClass.serializer(), response.bodyAsText())
    }
}