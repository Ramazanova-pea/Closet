package com.fanofstars.data.api.Auth

import com.fanofstars.data.api.Auth.model.AuthResponse
import com.fanofstars.data.api.Auth.model.CreateItemRequest
import com.fanofstars.data.api.Auth.model.CreateLookRequest
import com.fanofstars.data.api.Auth.model.ImagePathRequest
import com.fanofstars.data.api.Auth.model.ItemResponse
import com.fanofstars.data.api.Auth.model.LoginRequest
import com.fanofstars.data.api.Auth.model.LookResponse
import com.fanofstars.data.api.Auth.model.RegistrationRequest
import com.fanofstars.data.api.Auth.model.TagResponse
import com.fanofstars.data.api.Auth.model.UserResponse
import com.fanofstars.domain.model.Look
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AllApi {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("register")
    suspend fun register(@Body request: RegistrationRequest): AuthResponse

    @POST("getUserByToken")
    suspend fun getUserByToken(@Body request: Map<String, String>): UserResponse

    @GET("/tags")
    suspend fun getTags(): List<TagResponse>

    @POST("/uploadImagePath")
    suspend fun uploadImagePath(@Body request: ImagePathRequest)

    @POST("/createItem")
    suspend fun createItem(@Body request: CreateItemRequest)

    @POST("/getItems")
    suspend fun getItems(@Body request: Map<String, String>): List<ItemResponse>

    @POST("/createLook")
    suspend fun createLook(@Body request: CreateLookRequest): Response<Map<String, String>>

    @POST("/getLooks")
    suspend fun getLooks(@Body request: Map<String, String>): List<Look>
}