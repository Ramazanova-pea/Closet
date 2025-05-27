package ru.fan_of_stars.closet.ui.filter

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fanofstars.domain.usecases.GetTagsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TagsState(
    val tags: List<TagUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

// Intent
sealed class TagsIntent {
    data object LoadTags : TagsIntent()
    data class ToggleTag(val name: String) : TagsIntent()
    data object SaveSelectedTags : TagsIntent()
}

class FilterViewModel(
    private val getTagsUseCase: GetTagsUseCase,
    application: Application
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(TagsState())
    val state: StateFlow<TagsState> = _state.asStateFlow()

    private val selectedTags = mutableSetOf<String>()

    fun handleIntent(intent: TagsIntent) {
        when (intent) {
            is TagsIntent.LoadTags -> loadTags()
            is TagsIntent.ToggleTag -> toggleTag(intent.name)
            is TagsIntent.SaveSelectedTags -> saveSelectedTags()
        }
    }

    private fun loadTags() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val tags = getTagsUseCase()

                val context = getApplication<Application>().applicationContext
                val prefs = context.getSharedPreferences("selected-tags", Context.MODE_PRIVATE)
                val savedTags = prefs.getStringSet("selected_tags", emptySet()) ?: emptySet()

                // ⚠️ Вот здесь обновляем selectedTags!
                selectedTags.clear()
                selectedTags.addAll(savedTags)

                _state.update {
                    it.copy(
                        tags = tags.map { tagName ->
                            TagUiModel(
                                id = "",
                                name = tagName,
                                isSelected = savedTags.contains(tagName)
                            )
                        },
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }


    private fun toggleTag(name: String) {
        _state.update { currentState ->
            val updatedTags = currentState.tags.map {
                if (it.name == name) it.copy(isSelected = !it.isSelected)
                else it
            }
            if (updatedTags.find { it.name == name }?.isSelected == true) {
                selectedTags.add(name)
            } else {
                selectedTags.remove(name)
            }
            currentState.copy(tags = updatedTags)
        }
    }

    private fun saveSelectedTags() {
        println("Selected tags: $selectedTags")

        val context = getApplication<Application>().applicationContext
        val prefs = context.getSharedPreferences("selected-tags", Context.MODE_PRIVATE)
        prefs.edit().putStringSet("selected_tags", selectedTags).apply()
    }


}

data class TagUiModel(
    val id: String,
    val name: String,
    val isSelected: Boolean = false // Поле, которое нужно ТОЛЬКО для UI
)
