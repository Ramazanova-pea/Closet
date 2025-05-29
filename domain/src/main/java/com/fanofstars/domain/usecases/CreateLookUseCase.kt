package com.fanofstars.domain.usecases

import com.fanofstars.domain.repositories.LookRepository

class CreateLookUseCase(
    private val repository: LookRepository
) {
    suspend operator fun invoke(name: String, notes: String?, items: List<String>): Result<String> {
        return repository.createLook(name, notes, items)
    }
}