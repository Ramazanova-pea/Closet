package com.fanofstars.data.api.Auth.model

data class RegistrationRequest(
    val login: String,
    val username: String,
    val email: String,
    val password: String,
)
