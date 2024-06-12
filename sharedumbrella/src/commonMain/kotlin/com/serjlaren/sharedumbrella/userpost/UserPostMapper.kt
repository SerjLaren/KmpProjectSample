package com.serjlaren.sharedumbrella.userpost

import com.serjlaren.KmpProjectSample.core.network.userpost.UserPostDto
import com.serjlaren.KmpProjectSample.core.network.userpost.UserPostRequestBody
import data.UserPostDbo

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