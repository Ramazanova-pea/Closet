package com.fanofstars.domain.usecases

import com.fanofstars.domain.model.Item
import com.fanofstars.domain.repositories.ItemRepository

class CreateItemUseCase(private val repository: ItemRepository) {
    suspend operator fun invoke(item: Item) {
        repository.createItem(item)
    }
}