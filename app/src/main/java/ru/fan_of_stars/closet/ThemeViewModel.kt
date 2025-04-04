package ru.fan_of_stars.closet

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeViewModel : ViewModel(){
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean>
        get() = _isDarkTheme
    init {
        Log.d("ThemeViewModel", "init")
    }

    fun switchTheme(){
        _isDarkTheme.value = !_isDarkTheme.value
    }
}