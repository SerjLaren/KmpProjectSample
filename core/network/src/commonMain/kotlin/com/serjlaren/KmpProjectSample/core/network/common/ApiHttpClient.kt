package com.serjlaren.KmpProjectSample.core.network.common

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

internal object ApiHttpClient {

    const val BASE_URL = "jsonplaceholder.typicode.com"

    val client = HttpClient(CIO) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 10000L
                connectTimeoutMillis = 10000L
                socketTimeoutMillis = 10000L
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_URL
                }
                header("X-Custom-Header", "Hello") // TODO Adding header just for test
                header(HttpHeaders.Authorization, "abc123") // TODO Adding header just for test
            }
        }

    // TODO This function below is just for test, in future need to delete it

    @OptIn(InternalSerializationApi::class)
    suspend fun <T : Any> getCustom(
        url: String,
        responseClass: KClass<T>,
        query: List<Pair<String, String>> = listOf(),
    ): T {
        val response = client.get(url) {
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