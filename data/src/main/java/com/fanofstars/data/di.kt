package com.fanofstars.data

import com.fanofstars.data.api.Auth.AuthRepositoryImpl
import com.fanofstars.domain.repositories.AuthRepository
import org.koin.dsl.module

val appModule = module {
    single <AuthRepository> { AuthRepositoryImpl(get()) }
}