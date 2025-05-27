package com.fanofstars.data.api.Auth

import com.fanofstars.domain.repositories.TagsRepository

class TagsRepositoryImpl(
    private val api: AllApi
) : TagsRepository {
    override suspend fun getTags(): List<String> {
        return api.getTags().map { it.name }
    }
}