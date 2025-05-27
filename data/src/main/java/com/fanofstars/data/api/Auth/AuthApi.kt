package com.fanofstars.data.api.Auth

import com.fanofstars.data.api.Auth.model.AuthResponse
import com.fanofstars.data.api.Auth.model.LoginRequest
import com.fanofstars.data.api.Auth.model.RegistrationRequest
import com.fanofstars.data.api.Auth.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("register")
    suspend fun register(@Body request: RegistrationRequest): AuthResponse

    @POST("getUserByToken")
    suspend fun getUserByToken(@Body request: Map<String, String>): UserResponse
}