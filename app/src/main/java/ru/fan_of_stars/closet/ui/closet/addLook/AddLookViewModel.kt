package ru.fan_of_stars.closet.ui.closet.addLook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fanofstars.domain.usecases.CreateLookUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddLookViewModel(
    private val createLookUseCase: CreateLookUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AddLookState>(AddLookState())
    val state: StateFlow<AddLookState> = _state.asStateFlow()

    fun createLook(name: String, notes: String?, items: List<String>) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = createLookUseCase(name, notes, items)
            result.onSuccess { idLook ->
                _state.update { it.copy(isLoading = false, success = true, createdLookId = idLook) }
            }.onFailure { e ->
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }
}

data class AddLookState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val createdLookId: String? = null,
    val errorMessage: String? = null
)