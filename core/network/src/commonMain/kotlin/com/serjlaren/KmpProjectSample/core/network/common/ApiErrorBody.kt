package com.serjlaren.KmpProjectSample.core.network.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorBody(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
)