package com.fanofstars.data.mapper

import com.fanofstars.data.api.Auth.model.RegistrationRequest
import com.fanofstars.domain.model.RegistrationData

fun RegistrationData.toRequest(): RegistrationRequest {
    return RegistrationRequest(
        login = login,
        username = username,
        email = email,
        password = password
    )
}
