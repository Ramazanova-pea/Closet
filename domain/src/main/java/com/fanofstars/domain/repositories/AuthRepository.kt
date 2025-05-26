package com.fanofstars.domain.repositories

import com.fanofstars.domain.model.LoginData
import com.fanofstars.domain.model.RegistrationData
import com.fanofstars.domain.model.UserData

interface AuthRepository {
    suspend fun registerUser( data: RegistrationData) : Result<UserData>
    suspend fun loginUser(data: LoginData): Result<UserData>
}