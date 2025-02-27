package ru.fan_of_stars.closet.ui.closet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ClosetScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow<ClosetScreenStates>(ClosetScreenStates.StateClothes)
    val state : StateFlow<ClosetScreenStates>
        get() = _state

    var showAddClothes by mutableStateOf(false)
        private set

    fun onLooksClick() {
        _state.value = ClosetScreenStates.StateLooks
    }

    fun onClothesClick() {
        _state.value = ClosetScreenStates.StateClothes
        showAddClothes = true
    }

    fun showAddClothes() {
        showAddClothes = true
    }

    fun closeAddClothes() {
        showAddClothes = false
    }
}