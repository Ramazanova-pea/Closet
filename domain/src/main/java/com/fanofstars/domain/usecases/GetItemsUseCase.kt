package com.fanofstars.domain.usecases

import com.fanofstars.domain.model.Item
import com.fanofstars.domain.repositories.ItemRepository

class GetItemsUseCase(private val repository: ItemRepository) {
    suspend operator fun invoke(token: String): List<Item> {
        return repository.getItems(token)
    }
}