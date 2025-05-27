package com.fanofstars.data.mapper

import com.fanofstars.data.api.Auth.model.UserResponse
import com.fanofstars.domain.model.UserData

fun UserResponse.toEntity(): UserData {
    return UserData(
        id = id_user,
        login = login,
        username = username,
        email = email,
        password = password,
        token = token
    )
}