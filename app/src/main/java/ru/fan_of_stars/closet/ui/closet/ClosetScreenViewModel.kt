package ru.fan_of_stars.closet.ui.closet

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ClosetScreenViewModel : ViewModel() {
    var showClothes by mutableStateOf(true)
        private set

    var showAddClothes by mutableStateOf(false)
        private set

    fun onLooksClick() {
        showClothes = false
    }

    fun onClothesClick() {
        showClothes = true
        showAddClothes = true
    }

    fun showAddClothes() {
        showAddClothes = true
    }

    fun closeAddClothes() {
        showAddClothes = false
    }


}

