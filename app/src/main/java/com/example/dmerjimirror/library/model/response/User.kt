package com.example.dmerjimirror.library.model.response

import java.io.Serializable

data class UserResponse(val token: String, val user: User): Serializable

data class User(
    val id: Int,
    val username: String,
    val fullname: String,
    val email: String,
    val password: String?,
    val avatar: String,
    val timeformat: Int,
    val unit: Int
): Serializable
