package com.fanofstars.domain.usecases

import com.fanofstars.domain.model.LoginData
import com.fanofstars.domain.model.UserData
import com.fanofstars.domain.repositories.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(data: LoginData): Result<UserData> {
        return repository.loginUser(data)
    }
}