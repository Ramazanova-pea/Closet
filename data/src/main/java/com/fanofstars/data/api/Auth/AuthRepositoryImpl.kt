package com.fanofstars.data.api.Auth

import com.fanofstars.data.mapper.toDomain
import com.fanofstars.data.mapper.toRequest
import com.fanofstars.domain.model.LoginData
import com.fanofstars.domain.model.RegistrationData
import com.fanofstars.domain.model.UserData
import com.fanofstars.domain.repositories.AuthRepository

class AuthRepositoryImpl (
    private val allApi: AllApi
) : AuthRepository {
    override suspend fun registerUser(data: RegistrationData): Result<UserData> {
        val request = data.toRequest()
        return try {
            val response = allApi.register(request)
            Result.success(response.toDomain())
        } catch (e: retrofit2.HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
            Result.failure(Exception(errorMessage))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginUser(data: LoginData): Result<UserData> {
        val request = data.toRequest()
        return try {
            val response = allApi.login(request)
            Result.success(response.toDomain())
        } catch (e: retrofit2.HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
            Result.failure(Exception(errorMessage))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}