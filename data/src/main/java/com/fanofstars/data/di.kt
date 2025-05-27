package com.fanofstars.data

import com.fanofstars.data.api.Auth.AllApi
import com.fanofstars.data.api.Auth.AuthRepositoryImpl
import com.fanofstars.data.api.Auth.TagsRepositoryImpl
import com.fanofstars.data.api.Auth.UserRepositoryImpl
import com.fanofstars.domain.repositories.AuthRepository
import com.fanofstars.domain.repositories.TagsRepository
import com.fanofstars.domain.repositories.UserRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val baseUrl = "https://ktor-closet.onrender.com/"

val dataModule = module {
    single<OkHttpClient> { OkHttpClient() }
    single <AuthRepository> { AuthRepositoryImpl(get()) }
    single <UserRepository> { UserRepositoryImpl(get(), get()) }
    single <TagsRepository> { TagsRepositoryImpl(get()) }
    single <Retrofit>  {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single<AllApi> { get<Retrofit>().create(AllApi::class.java) }
}

