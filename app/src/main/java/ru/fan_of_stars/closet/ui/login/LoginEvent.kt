package ru.fan_of_stars.closet.ui.login

sealed class LoginEvent {
    data class OnLoginChange(val login: String) : LoginEvent()
    data class OnPasswordChange(val password: String) : LoginEvent()
    object Submit : LoginEvent()
}