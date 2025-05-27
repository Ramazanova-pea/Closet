package com.fanofstars.domain.repositories

import com.fanofstars.domain.model.UserData

interface UserRepository {
    suspend fun getUserByToken(): UserData
}