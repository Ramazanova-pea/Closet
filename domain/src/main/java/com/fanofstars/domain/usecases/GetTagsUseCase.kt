package com.fanofstars.domain.usecases

import com.fanofstars.domain.repositories.TagsRepository

class GetTagsUseCase(
    private val tagsRepository: TagsRepository
) {
    suspend operator fun invoke(): List<String> {
        return tagsRepository.getTags()
    }
}