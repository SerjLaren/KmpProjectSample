package com.serjlaren.KmpProjectSample.core.network.userpost

import com.serjlaren.KmpProjectSample.core.network.common.ApiHttpClient
import com.serjlaren.KmpProjectSample.core.network.common.MyApiResponse
import com.serjlaren.KmpProjectSample.core.network.common.safeGet
import com.serjlaren.KmpProjectSample.core.network.common.safePost
import io.ktor.client.HttpClient

class UserPostApi {

    private val httpClient: HttpClient = ApiHttpClient.client

    suspend fun getPosts(): MyApiResponse<List<UserPostDto>> {
        return httpClient.safeGet<List<UserPostDto>>(
            url = "posts",
            queryParams = mapOf("token" to "abc123"), // TODO query params just for example
        )
    }

    suspend fun postPost(post: UserPostRequestBody): MyApiResponse<UserPostDto> {
        return httpClient.safePost<UserPostDto>(
            url = "posts",
            requestBody = post,
        )
    }
}