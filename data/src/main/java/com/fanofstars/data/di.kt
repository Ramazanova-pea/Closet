package com.fanofstars.data

import com.fanofstars.data.api.Auth.AuthApi
import com.fanofstars.data.api.Auth.AuthRepositoryImpl
import com.fanofstars.data.api.Auth.model.AuthResponse
import com.fanofstars.data.api.Auth.model.RegistrationRequest
import com.fanofstars.domain.repositories.AuthRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body

private const val baseUrl = "https://ktor-closet.onrender.com/"

val dataModule = module {
    single<OkHttpClient> { OkHttpClient() }
    single <AuthRepository> { AuthRepositoryImpl(get()) }
    single <Retrofit>  {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single<AuthApi> { get<Retrofit>().create(AuthApi::class.java) }
}

