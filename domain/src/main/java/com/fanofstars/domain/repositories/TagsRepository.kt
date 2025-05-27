package com.fanofstars.domain.repositories


interface TagsRepository {
    suspend fun getTags(): List<String>
}