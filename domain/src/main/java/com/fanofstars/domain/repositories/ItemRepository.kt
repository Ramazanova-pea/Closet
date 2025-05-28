package com.fanofstars.domain.repositories

import com.fanofstars.domain.model.Item

interface ItemRepository {
    suspend fun createItem(item: Item)
    suspend fun getItems(token: String): List<Item>
}