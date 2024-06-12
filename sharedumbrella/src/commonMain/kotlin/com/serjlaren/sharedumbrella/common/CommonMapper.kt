package com.serjlaren.sharedumbrella.common

import com.serjlaren.KmpProjectSample.core.network.common.ApiErrorBody
import com.serjlaren.KmpProjectSample.core.network.common.ApiResponse

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
