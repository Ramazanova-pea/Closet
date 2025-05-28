package com.fanofstars.domain.repositories

interface ImageRepository {
    suspend fun uploadImagePath(path: String)
}