package ru.fan_of_stars.closet.ui.login

data class LoginScreenState(
    val id: String? = null,
    val login: String = "",
    val password: String = "",
    val token: String? = null,
    val username: String? = null,
    val email: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
