package com.fanofstars.domain.usecases


import com.fanofstars.domain.repositories.ImageRepository

class UploadImagePathUseCase(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(path: String) {
        repository.uploadImagePath(path)
    }
}