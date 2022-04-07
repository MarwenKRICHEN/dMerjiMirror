package com.example.dmerjimirror.library.model

data class User(
    val id: Int,
    val username: String,
    val fullname: String,
    val email: String,
    val password: String,
    val avatar: String,
    val timeFormat: Int,
    val unit: Int
)
