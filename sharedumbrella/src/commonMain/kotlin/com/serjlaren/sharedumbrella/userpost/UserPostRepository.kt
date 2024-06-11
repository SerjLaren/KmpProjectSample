package com.serjlaren.sharedumbrella.userpost

import com.serjlaren.KmpProjectSample.core.network.userpost.UserPostApi
import com.serjlaren.core.storage.dao.UserPostDao
import com.serjlaren.sharedumbrella.common.RemoteResult
import com.serjlaren.sharedumbrella.common.toDbo
import com.serjlaren.sharedumbrella.common.toRemoteResult
import com.serjlaren.sharedumbrella.common.toPostBody
import com.serjlaren.sharedumbrella.common.toUserPost

// WARNING! Create this class as singleton to prevent multiple transactions, etc...
class UserPostRepository {

    private val userPostDao = UserPostDao()
    private val userPostApi = UserPostApi()

    suspend fun getUserPostsRemote(): RemoteResult<List<UserPost>> {
        return userPostApi.getPosts().toRemoteResult { map { it.toUserPost() } }
    }

    suspend fun sendUserPostRemote(userPost: UserPost): RemoteResult<UserPost> {
        return userPostApi.postPost(userPost.toPostBody()).toRemoteResult { toUserPost() }
    }

    suspend fun getUserPostsLocal(): List<UserPost> {
        return userPostDao.getPosts().map { it.toUserPost() }
    }

    suspend fun getUserPostLocal(id: Long): UserPost? {
        return userPostDao.getPost(id = id)?.toUserPost()
    }

    suspend fun saveUserPostsLocal(userPosts: List<UserPost>) {
        userPosts.forEach { userPost ->
            userPostDao.insertPost(userPost.toDbo())
        }
    }

    suspend fun deleteUserPostsLocal() {
        userPostDao.deletePosts()
    }
}