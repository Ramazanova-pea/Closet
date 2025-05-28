package ru.fan_of_stars.closet.ui.closet


import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.fan_of_stars.closet.ui.closet.card.ClothesCardScreen
import ru.fan_of_stars.closet.ui.closet.card.ItemViewModel
import ru.fan_of_stars.closet.ui.icons.*

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun ClosetScreenPreview() {
    ClosetScreen(navController = rememberNavController(), paddingValues = PaddingValues(16.dp))
}


@Composable
fun ClosetScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: ClosetScreenViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val pref = context.getSharedPreferences("selected-tags", Context.MODE_PRIVATE)
    val tags = pref.getStringSet("selected_tags", emptySet())?.toList() ?: emptyList()

    val selectedItems = remember { mutableStateListOf<String>() }

    val itemViewModel: ItemViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        itemViewModel.loadItems()
    }

    val showClothes = viewModel.showClothes
    val showAddClothes = viewModel.showAddClothes

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(navController)
            if (showClothes) {
                ClothesState(viewModel, navController)
                if (tags.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        items(tags.size) { index ->
                            val tag = tags[index]
                            Tag(text = tag) {
                                // Удаляем тег из SharedPreferences
                                removeTagFromPrefs(context, tag)
                                // Принудительно перерисовываем экран (через recomposition)
                                navController.navigate("closet_screen") {
                                    popUpTo("closet_screen") { inclusive = true }
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }

                if (itemViewModel.isLoading) {
                    // Только область карточек покажет индикатор
                    CircularProgressIndicator()
                } else {
                    val filteredItems = if (tags.isNotEmpty()) {
                        itemViewModel.items.filter { item ->
                            tags.all { tag -> item.tags.contains(tag) }
                        }
                    } else {
                        itemViewModel.items
                    }
                    if(filteredItems.isEmpty()){
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ){
                            Text(
                                text = "Одежда не найдена",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                    else{
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
                                ClothesCardScreen(
                                    navController = navController,
                                    item = item,
                                    selectedItems = selectedItems
                                )
                            }
                        }
                    }

                }

            } else {
                LooksState(viewModel, navController)
                // Здесь можешь добавить контент для Looks
            }


        }

        DownButton(navController, showClothes, selectedCount = selectedItems.size)

        if (showAddClothes) {
            // AddClothes()
        }
    }
}



@Composable
fun ClothesState(viewModel: ClosetScreenViewModel, navController: NavController, ) {
    //Выбор гардероб
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Clothes",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Looks",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable { viewModel.onLooksClick() }
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    navController.navigate("filter_screen")
                }
            ) {
                Icon(
                    imageVector = FilterIC,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = { navController.navigate("search_screen") }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp,
        )
    }
}


@Composable
fun LooksState(viewModel: ClosetScreenViewModel, navController: NavController) {
    //Выбор образы
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Clothes",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable { viewModel.onClothesClick() }
            )
            Text(
                text = "Looks",
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    navController.navigate("filter_screen")
                }
            ) {
                Icon(
                    imageVector = FilterIC,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = { navController.navigate("search_screen") }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp,
        )
    }

}

@Composable
fun DownButton(
    navController: NavController,
    showClothes: Boolean,
    selectedCount: Int
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(100.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.outline
                        )
                    )
                )
        )

        FloatingActionButton(
            onClick = {
                if (selectedCount >= 2) {
                    // Логика для выбранных элементов
                    Toast.makeText(navController.context, "Действие с $selectedCount элементами", Toast.LENGTH_SHORT).show()
                } else {
                    // Старая логика
                    if (showClothes) navController.navigate("add_item_screen")
                    else Toast.makeText(navController.context, "Добавить образ", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .shadow(8.dp, CircleShape),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun Header(navController: NavController) {
    //Заголовок
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .fillMaxWidth()
            .padding(8.dp),

    ) {
        Text(
            text = "Closet",
            modifier = Modifier.align(Alignment.Center),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,

        )
        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = { navController.navigate("settings_screen") }
        ) {
            Image(
                SettingIC,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
        }

    }
}

@Composable
fun Tag(
    text: String,
    onRemoveClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(start = 8.dp, end = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                CloseIC,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer),
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onRemoveClick() } // 👈 вот тут!
            )
            Text(
                text = text,
                modifier = Modifier.padding(2.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

fun removeTagFromPrefs(context: Context, tag: String) {
    val prefs = context.getSharedPreferences("selected-tags", Context.MODE_PRIVATE)
    val savedTags = prefs.getStringSet("selected_tags", emptySet())?.toMutableSet() ?: mutableSetOf()
    savedTags.remove(tag)
    prefs.edit().putStringSet("selected_tags", savedTags).apply()
}

