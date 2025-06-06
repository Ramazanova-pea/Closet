package com.fanofstars.domain.usecases

import com.fanofstars.domain.model.RegistrationData
import com.fanofstars.domain.model.UserData
import com.fanofstars.domain.repositories.AuthRepository

class RegistrationUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(data: RegistrationData): Result<UserData> {

        return repository.registerUser(data)
    }
}