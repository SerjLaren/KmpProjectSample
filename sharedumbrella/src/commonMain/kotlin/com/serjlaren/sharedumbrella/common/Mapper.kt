package com.serjlaren.sharedumbrella.common

import com.serjlaren.KmpProjectSample.core.network.common.ApiErrorBody
import com.serjlaren.KmpProjectSample.core.network.common.ApiResponse
import com.serjlaren.KmpProjectSample.core.network.userpost.UserPostDto
import com.serjlaren.KmpProjectSample.core.network.userpost.UserPostRequestBody
import com.serjlaren.sharedumbrella.userpost.UserPost
import data.UserPostDbo

internal fun <T, E> ApiResponse<T, ApiErrorBody>.toRemoteResult(mapBlock: T.() -> E): RemoteResult<E> {
    return when (this) {
        is ApiResponse.Success -> {
            RemoteResult.Success(this.body.mapBlock())
        }
        is ApiResponse.Error.HttpError -> {
            RemoteResult.Error.ServerError(
                RemoteError(
                    httpCode = this.code,
                    serverCode = this.errorBody?.code,
                    message = this.errorBody?.message.orEmpty(),
                )
            )
        }
        ApiResponse.Error.NetworkError -> {
            RemoteResult.Error.NetworkError
        }
        ApiResponse.Error.SerializationError -> {
            RemoteResult.Error.SerializationError
        }
    }
}

internal fun UserPostDto.toUserPost(): UserPost {
    return UserPost(
        id = this.id,
        userId = this.userId,
        title = this.title,
        body = this.body,
    )
}

internal fun UserPostDbo.toUserPost(): UserPost {
    return UserPost(
        id = this._id,
        userId = this.userId,
        title = this.title,
        body = this.body,
    )
}

internal fun UserPost.toDbo(): UserPostDbo {
    return UserPostDbo(
        _id = this.id,
        userId = this.userId,
        title = this.title,
        body = this.body,
    )
}

internal fun UserPost.toDto(): UserPostDto {
    return UserPostDto(
        id = this.id,
        userId = this.userId,
        title = this.title,
        body = this.body,
    )
}

internal fun UserPost.toPostBody(): UserPostRequestBody {
    return UserPostRequestBody(
        userId = this.userId,
        title = this.title,
        body = this.body,
    )
}