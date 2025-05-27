package com.fanofstars.data.api.Auth

import android.content.SharedPreferences
import com.fanofstars.data.mapper.toEntity
import com.fanofstars.domain.model.UserData
import com.fanofstars.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val apiService: AllApi,
    private val sharedPreferences: SharedPreferences
) : UserRepository {

    override suspend fun getUserByToken(): UserData {
        val token = sharedPreferences.getString("token", null)
            ?: throw IllegalStateException("No token found")

        // Оборачиваем токен в Map
        val request = mapOf("token" to token)

        // Отправляем Map как тело запроса
        val response = apiService.getUserByToken(request)

        return response.toEntity()
    }
}
