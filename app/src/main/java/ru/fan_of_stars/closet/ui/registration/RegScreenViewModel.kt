package ru.fan_of_stars.closet.ui.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fanofstars.data.api.Auth.model.RegistrationRequest
import com.fanofstars.domain.model.RegistrationData
import com.fanofstars.domain.usecases.RegistrationUseCase
import kotlinx.coroutines.launch


class RegScreenViewModel(
    private val registerUserUseCase: RegistrationUseCase,
) : ViewModel() {
    var state by mutableStateOf(RegScreenStates())
        private set

    fun OnEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnUsernameChange -> {
                state = state.copy(username = event.username)
            }
            is RegistrationEvent.OnLoginChange -> {
                state = state.copy(login = event.login)
            }
            is RegistrationEvent.OnEmailChange -> {
                state = state.copy(email = event.email)
            }
            is RegistrationEvent.OnPasswordChange -> {
                state = state.copy(password = event.password)
            }
            RegistrationEvent.Submit -> {
                registerUser()
            }
        }
    }

    private fun registerUser() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            val data = RegistrationData(
                login = state.login,
                username = state.username,
                email = state.email,
                password = state.password
            )
            val result = registerUserUseCase.invoke(data)
            if (result.isSuccess) {
                state = state.copy(token = result.getOrNull(), isLoading = false)
            } else {
                state = state.copy(error = result.exceptionOrNull()?.message, isLoading = false)
            }
        }
    }
}