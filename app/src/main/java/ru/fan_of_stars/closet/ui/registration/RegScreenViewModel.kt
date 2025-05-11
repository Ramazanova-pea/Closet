package ru.fan_of_stars.closet.ui.registration

import android.util.Log
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
                Log.d("RegScreen", "OnUsernameChange: ${event.username}")
                state = state.copy(username = event.username)
            }
            is RegistrationEvent.OnLoginChange -> {
                Log.d("RegScreen", "OnLoginChange: ${event.login}")
                state = state.copy(login = event.login)
            }
            is RegistrationEvent.OnEmailChange -> {
                Log.d("RegScreen", "OnEmailChange: ${event.email}")
                state = state.copy(email = event.email)
            }
            is RegistrationEvent.OnPasswordChange -> {
                Log.d("RegScreen", "OnPasswordChange: ${event.password}")
                state = state.copy(password = event.password)
            }
            RegistrationEvent.Submit -> {
                Log.d("RegScreen", "Submit clicked")
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
            Log.d("RegScreen", "Registration result: $result")
            if (result.isSuccess) {
                state = state.copy(token = result.getOrNull(), isLoading = false)
            } else {
                state = state.copy(error = result.exceptionOrNull()?.message, isLoading = false)
            }
        }
    }
}