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
            val response = authApi.register(request)
            Result.success(response.token)
        } catch (e: retrofit2.HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
            Result.failure(Exception(errorMessage))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}