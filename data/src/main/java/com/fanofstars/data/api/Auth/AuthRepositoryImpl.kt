package com.fanofstars.data.api.Auth

import com.fanofstars.data.api.Auth.model.RegistrationRequest
import com.fanofstars.data.mapper.toRequest
import com.fanofstars.domain.model.RegistrationData
import com.fanofstars.domain.repositories.AuthRepository

class AuthRepositoryImpl (
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun registerUser(data: RegistrationData): Result<String> {
        val request = data.toRequest()
        return try {
            val response = authApi.register(request).execute()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.token)
            } else {
                Result.failure(Exception("Registration failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}