package com.serjlaren.KmpProjectSample.core.network.userpost

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserPostRequestBody(
    @SerialName("userId")
    val userId: Int,
    @SerialName("title")
    val title: String,
    @SerialName("body")
    val body: String,
)