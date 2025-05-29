package com.fanofstars.domain.repositories

import com.fanofstars.domain.model.Look

interface LookRepository {
    suspend fun createLook(name: String, notes: String?, items: List<String>): Result<String>
    suspend fun getLooks(token: String): List<Look>
}