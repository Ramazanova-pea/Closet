package com.fanofstars.data.mapper

import com.fanofstars.data.api.Auth.model.AuthResponse
import com.fanofstars.domain.model.UserData

fun AuthResponse.toDomain(): UserData {
    return UserData(
        id = this.id,
        login = this.login,
        password = this.password,
        username = this.username,
        email = this.email,
        token = this.token
    )
}