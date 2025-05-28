package com.fanofstars.domain.repositories

import com.fanofstars.domain.model.Item

interface ItemRepository {
    suspend fun createItem(item: Item)
}