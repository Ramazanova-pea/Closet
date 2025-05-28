package ru.fan_of_stars.closet.ui.closet.card

import android.app.Application
import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fanofstars.domain.model.Item
import com.fanofstars.domain.repositories.ItemRepository
import com.fanofstars.domain.usecases.GetItemsUseCase
import kotlinx.coroutines.launch

class ItemViewModel(
    private val getItemsUseCase: GetItemsUseCase,
    private val application: Application
): AndroidViewModel(application) {
    val items = mutableStateListOf<Item>()
    var isLoading by mutableStateOf(false)
        private set

    fun loadItems() {
        viewModelScope.launch {
            isLoading = true
            try {
                val token = getApplication<Application>()
                    .getSharedPreferences("auth", Context.MODE_PRIVATE)
                    .getString("token", "") ?: ""

                val result = getItemsUseCase(token)
                items.clear()
                items.addAll(result)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    private val _selectedItem = mutableStateOf<Item?>(null)
    val selectedItem: State<Item?> = _selectedItem

    fun setItem(item: Item) {
        _selectedItem.value = item
    }
}