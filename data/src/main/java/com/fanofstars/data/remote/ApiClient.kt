package com.fanofstars.data.remote

import com.fanofstars.data.api.Auth.AllApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://ktor-closet.onrender.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val allApi: AllApi = retrofit.create(AllApi::class.java)
}