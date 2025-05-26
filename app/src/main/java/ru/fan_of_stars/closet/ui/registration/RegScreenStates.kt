package ru.fan_of_stars.closet.ui.registration

data class RegScreenStates (
    val username: String = "",
    val login: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val token: String? = null,
    val userId: String? = null, // 👈 Добавлено
    val error: String? = null
)