package ru.fan_of_stars.closet.ui.user

import com.fanofstars.domain.model.UserData

sealed class UserState {
    object Loading : UserState()
    data class Success(val user: UserData) : UserState()
    data class Error(val message: String) : UserState()
}