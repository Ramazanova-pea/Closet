package ru.fan_of_stars.closet.ui.registration

sealed class RegistrationEvent {
    data class OnUsernameChange(val username: String) : RegistrationEvent()
    data class OnLoginChange(val login: String) : RegistrationEvent()
    data class OnEmailChange(val email: String) : RegistrationEvent()
    data class OnPasswordChange(val password: String) : RegistrationEvent()
    object Submit : RegistrationEvent()
}