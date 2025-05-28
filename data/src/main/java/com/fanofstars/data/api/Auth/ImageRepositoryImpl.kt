package com.fanofstars.data.api.Auth

import com.fanofstars.data.api.Auth.model.ImagePathRequest
import com.fanofstars.domain.repositories.ImageRepository

class ImageRepositoryImpl(private val api: AllApi) : ImageRepository {
    override suspend fun uploadImagePath(path: String) {
        api.uploadImagePath(ImagePathRequest(path))
    }
}