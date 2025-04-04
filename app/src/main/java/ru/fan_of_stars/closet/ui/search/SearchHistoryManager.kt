package ru.fan_of_stars.closet.ui.search

import android.content.Context

class SearchHistoryManager(context: Context) {
    private val prefs = context.getSharedPreferences("search_history", Context.MODE_PRIVATE)

    companion object{
        private const val HISTORY_KEY = "search_history"
        private const val MAX_HISTORY_SIZE = 10
    }

    fun saveQuery(query: String){
        val history = getHistory().toMutableList()
        history.remove(query) // Удаляем дубликаты
        history.add(0, query) // Добавляем в начало

        if (history.size > MAX_HISTORY_SIZE) {
            history.removeAt(history.size - 1) // Ограничиваем историю до 10 элементов
        }

        val historyString = history.joinToString("|") // Сохраняем как строку
        prefs.edit().putString(HISTORY_KEY, historyString).apply()
    }

    fun getHistory(): List<String> {
        val historyString = prefs.getString(HISTORY_KEY, "") ?: ""
        return if (historyString.isNotEmpty()) historyString.split("|") else emptyList()
    }

    fun clearHistory() {
        prefs.edit().remove(HISTORY_KEY).apply()
    }
}