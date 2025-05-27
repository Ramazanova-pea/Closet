package ru.fan_of_stars.closet.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fanofstars.domain.usecases.GetUserByTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class UserViewModel(
    private val getUserByTokenUseCase: GetUserByTokenUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState: StateFlow<UserState> = _userState

    fun loadUserData() {
        viewModelScope.launch {
            try {
                val user = getUserByTokenUseCase()
                _userState.value = UserState.Success(user)
            } catch (e: Exception) {
                _userState.value = UserState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    companion object {
        fun provideFactory(
            getUserByTokenUseCase: GetUserByTokenUseCase
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return UserViewModel(getUserByTokenUseCase) as T
                }
            }
        }
    }
}