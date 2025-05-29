package ru.fan_of_stars.closet.ui.search

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fanofstars.domain.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import ru.fan_of_stars.closet.ui.closet.card.ClothesCardScreen
import ru.fan_of_stars.closet.ui.closet.card.ItemViewModel




@Composable
fun SearchScreen(
    navController: NavController,
    historyManager: SearchHistoryManager,
    paddingValues: PaddingValues
) {
    var searchText by remember { mutableStateOf("") }
    var history by remember { mutableStateOf(historyManager.getHistory()) }
    var isHistoryVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val itemViewModel: ItemViewModel = koinViewModel()
    var filteredItems by remember { mutableStateOf<List<Item>>(emptyList()) }
    var isSearchPerformed by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    // Выносим логику поиска в отдельную функцию
    fun performSearch(query: String) {
        if (query.isNotBlank()) {
            isLoading = true
            isHistoryVisible = false
            focusManager.clearFocus()
            isSearchPerformed = true

            filteredItems = itemViewModel.items.filter {
                it.name.contains(query, ignoreCase = true)
            }

            historyManager.saveQuery(query)
            history = historyManager.getHistory()
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Поиск") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { isHistoryVisible = it.isFocused && history.isNotEmpty() },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = {
                    performSearch(searchText)
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Поиск")
                }
            },
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = {
                        searchText = ""
                        filteredItems = emptyList()
                        isSearchPerformed = false
                    }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Очистить")
                    }
                }
            },
            interactionSource = interactionSource
        )

        // Индикатор загрузки
        if (isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                androidx.compose.material3.CircularProgressIndicator()
            }
        }


        // Сетка результатов или сообщение "Ничего не найдено"
        if (!isLoading && isSearchPerformed) { // <--- Используем флаг, а не просто текст
            if (filteredItems.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(filteredItems.size) { index ->
                        val item = filteredItems[index]
                        ClothesCardScreen(navController, item)
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ничего не найдено",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }



        // История поиска
        if (isHistoryVisible && !isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                history.forEach { item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                searchText = item
                                performSearch(item) // 🔥 Сразу начинаем поиск
                            }
                            .padding(top = 8.dp, bottom = 8.dp, start = 32.dp, end = 16.dp),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )
                }
            }
            TextButton(
                onClick = {
                    historyManager.clearHistory()
                    history = emptyList()
                    isHistoryVisible = false
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Очистить историю")
            }
        }
    }
}
