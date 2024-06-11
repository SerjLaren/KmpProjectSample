package com.serjlaren.sharedumbrella.userpost

data class UserPost(
    val id: Long,
    val userId: Long,
    val title: String,
    val body: String,
)