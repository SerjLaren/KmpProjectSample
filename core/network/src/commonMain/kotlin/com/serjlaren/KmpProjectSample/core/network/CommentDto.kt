package com.serjlaren.KmpProjectSample.core.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentDto(
    @SerialName("id")
    val id: Int,
    @SerialName("postId")
    val postId: Int,
    @SerialName("userId")
    val userId: Int,
    @SerialName("comment")
    val comment: String,
)