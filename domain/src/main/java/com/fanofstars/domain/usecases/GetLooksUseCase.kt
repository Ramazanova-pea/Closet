package com.fanofstars.domain.usecases

import com.fanofstars.domain.model.Look
import com.fanofstars.domain.repositories.LookRepository

class GetLooksUseCase(private val repository: LookRepository) {
    suspend operator fun invoke(token: String): List<Look> = repository.getLooks(token)
}