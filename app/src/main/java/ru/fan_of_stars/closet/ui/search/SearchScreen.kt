package ru.fan_of_stars.closet.ui.search

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun SearchScreenPreview() {
    SearchScreen(historyManager = SearchHistoryManager(LocalContext.current), paddingValues = PaddingValues(16.dp))
}


@Composable
fun SearchScreen(historyManager: SearchHistoryManager, paddingValues: PaddingValues){
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }
    var history by remember { mutableStateOf(historyManager.getHistory()) }
    var isHistoryVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }


    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ){
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Поиск") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { isHistoryVisible = it.isFocused && history.isNotEmpty()  },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = {
                    if (searchText.isNotBlank()) {
                        isLoading = true // Показываем индикатор загрузки
                        isHistoryVisible = false
                        focusManager.clearFocus()

                        // Симулируем "поиск"
                        kotlinx.coroutines.GlobalScope.launch {
                            delay(1500) // Задержка 1.5 секунды
                            withContext(Dispatchers.Main) {
                                historyManager.saveQuery(searchText)
                                history = historyManager.getHistory()
                                isLoading = false // Прячем индикатор
                            }
                        }
                    }
                    isHistoryVisible = false
                    focusManager.clearFocus()
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Поиск")
                }
            },
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = { searchText = "" }) {
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

        if(isHistoryVisible && !isLoading){
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
                                historyManager.saveQuery(item)
                                history = historyManager.getHistory()
                                isHistoryVisible = false
                                focusManager.clearFocus()
                            }
                            .padding(top = 8.dp, bottom = 8.dp, start = 32.dp, end = 16.dp),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Divider(
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
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