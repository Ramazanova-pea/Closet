package com.fanofstars.domain.repositories

import com.fanofstars.domain.model.RegistrationData

interface AuthRepository {
    suspend fun registerUser( data: RegistrationData) : Result<String>
}