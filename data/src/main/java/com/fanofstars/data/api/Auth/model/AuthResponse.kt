package com.fanofstars.data.api.Auth.model

data class AuthResponse(
    val id: String,
    val login: String,
    val password: String,
    val username: String,
    val email: String,
    val token: String,
)
