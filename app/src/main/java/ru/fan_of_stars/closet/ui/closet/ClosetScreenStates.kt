package ru.fan_of_stars.closet.ui.closet

sealed class ClosetScreenStates {
    data object StateLooks : ClosetScreenStates()
    data object StateClothes : ClosetScreenStates()
    data object StateIdle : ClosetScreenStates()
}