package com.fanofstars.data.api.Auth.model

data class UserResponse(
    val id_user: String,
    val login: String,
    val username: String,
    val email: String,
    val password: String,
    val token: String
)
