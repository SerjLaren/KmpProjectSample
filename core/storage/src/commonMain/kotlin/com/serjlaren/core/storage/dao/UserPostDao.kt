package com.serjlaren.core.storage.dao

import com.serjlaren.core.storage.AppDatabase
import data.UserPostDbo

class UserPostDao {

    private val appDatabase = AppDatabase.database

    fun getPosts(): List<UserPostDbo> {
        return appDatabase.userPostDboQueries.getAll().executeAsList()
    }

    fun getPost(id: Long): UserPostDbo? {
        return appDatabase.userPostDboQueries.getUserPost(id).executeAsOneOrNull()
    }

    fun insertPost(userPost: UserPostDbo) {
        appDatabase.userPostDboQueries.insertUserPost(
            _id = userPost._id,
            userId = userPost.userId,
            title = userPost.title,
            body = userPost.body,
        )
    }

    fun deletePosts() {
        appDatabase.userPostDboQueries.deleteAll()
    }
}