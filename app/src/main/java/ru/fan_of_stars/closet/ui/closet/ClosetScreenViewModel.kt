package ru.fan_of_stars.closet.ui.closet

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fanofstars.domain.model.Look
import com.fanofstars.domain.usecases.GetLooksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClosetScreenViewModel(
    private val getLooksUseCase: GetLooksUseCase,
    private val application: Application
) : AndroidViewModel(application) {
    private val _state = MutableStateFlow<ClosetScreenStates>(ClosetScreenStates.StateIdle)
    val state: StateFlow<ClosetScreenStates> = _state.asStateFlow()

    private val _looks = MutableStateFlow<List<Look>>(emptyList())
    val looks: StateFlow<List<Look>> = _looks.asStateFlow()

    var showClothes by mutableStateOf(true)
        private set

    var showAddClothes by mutableStateOf(false)
        private set

    fun onLooksClick() {
        showClothes = false
        loadLooks()
    }

    fun onClothesClick() {
        showClothes = true
        showAddClothes = true
    }

    private fun loadLooks() {
        _state.value = ClosetScreenStates.StateIdle
        viewModelScope.launch {
            try {
                val sharedPref = application.getSharedPreferences("auth", Context.MODE_PRIVATE)
                val token = sharedPref.getString("token", "") ?: ""
                android.util.Log.d("ClosetVM", "Загружаем образы, токен: $token")
                val response = getLooksUseCase(token)
                android.util.Log.d("ClosetVM", "Ответ сервера: $response")
                _looks.value = response
                if (response.isEmpty()) {
                    android.widget.Toast.makeText(application, "Образы не найдены", Toast.LENGTH_SHORT).show()
                } else {
                    android.widget.Toast.makeText(application, "Загружено ${response.size} образов", Toast.LENGTH_SHORT).show()
                }
                _state.value = ClosetScreenStates.StateLooks
            } catch (e: Exception) {
                e.printStackTrace()
                android.util.Log.e("ClosetVM", "Ошибка загрузки: ${e.localizedMessage}")
                android.widget.Toast.makeText(application, "Ошибка загрузки образов", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

