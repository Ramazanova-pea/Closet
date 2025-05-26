package com.fanofstars.data.mapper

import com.fanofstars.data.api.Auth.model.LoginRequest
import com.fanofstars.domain.model.LoginData

fun LoginData.toRequest(): LoginRequest {
    return LoginRequest(
        login = login,
        password = password
    )
}
