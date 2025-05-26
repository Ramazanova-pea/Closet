package com.fanofstars.data.mapper

import com.fanofstars.data.api.Auth.model.AuthResponse
import com.fanofstars.domain.model.UserData

fun AuthResponse.toDomain(): UserData {
    return UserData(
        id = id,
        login = login,
        username = username,
        email = email,
        token = token
    )
}