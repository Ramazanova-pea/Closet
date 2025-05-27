package com.fanofstars.domain.usecases

import com.fanofstars.domain.model.UserData
import com.fanofstars.domain.repositories.UserRepository

class GetUserByTokenUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): UserData {
        return userRepository.getUserByToken()
    }
}
