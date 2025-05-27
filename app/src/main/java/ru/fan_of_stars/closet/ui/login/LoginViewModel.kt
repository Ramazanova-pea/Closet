package ru.fan_of_stars.closet.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fanofstars.domain.model.LoginData
import com.fanofstars.domain.usecases.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    var state by mutableStateOf(LoginScreenState())
        private set

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLoginChange -> {
                state = state.copy(login = event.login)
            }
            is LoginEvent.OnPasswordChange -> {
                state = state.copy(password = event.password)
            }
            LoginEvent.Submit -> {
                loginUser()
            }
        }
    }

    private fun loginUser() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            val data = LoginData(
                login = state.login,
                password = state.password
            )

            val result = loginUseCase(data)

            if (result.isSuccess) {
                val user = result.getOrNull()
                if(user != null){
                    state = state.copy(
                        login = state.login,
                        password = state.password,
                        id = user.id,
                        token = user.token,
                        username = user.username,
                        email = user.email,
                        isLoading = false
                    )
                }
                else {
                    state = state.copy(
                        error = "User not found",
                        isLoading = false
                    )
                }

            } else {
                state = state.copy(
                    error = result.exceptionOrNull()?.message ?: "Unknown error",
                    isLoading = false
                )
            }
        }
    }

}
