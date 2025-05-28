package com.fanofstars.data.api.Auth

import com.fanofstars.data.api.Auth.model.CreateItemRequest
import com.fanofstars.domain.model.Item
import com.fanofstars.domain.repositories.ItemRepository

class ItemRepositoryImpl(private val api: AllApi) : ItemRepository {
    override suspend fun createItem(item: Item) {
        val request = CreateItemRequest(
            token = item.token,
            name = item.name,
            imagePath = item.imagePath,
            tags = item.tags,
            notes = item.notes
        )
        api.createItem(request)
    }

    override suspend fun getItems(token: String): List<Item> {
        val response = api.getItems(mapOf("token" to token))
        return response.map { itemResponse ->
            Item(
                token = token,
                name = itemResponse.name,
                imagePath = itemResponse.imagePath,
                tags = itemResponse.tags,
                notes = itemResponse.notes ?: ""
            )
        }
    }
}
