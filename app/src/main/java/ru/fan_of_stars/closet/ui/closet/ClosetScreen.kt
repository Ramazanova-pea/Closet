package ru.fan_of_stars.closet.ui.closet


import AppTheme
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.fan_of_stars.closet.ui.icons.*
import ru.fan_of_stars.closet.ui.theme.*

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
    viewModel: ClosetScreenViewModel = ClosetScreenViewModel(),
    paddingValues: PaddingValues,
) {
    val state = viewModel.state.collectAsState()
//    val state = ClosetScreenStates.StateLooks
    val showAddClothes = remember { mutableStateOf(viewModel.showAddClothes) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(navController)
        when (state.value) {
            is ClosetScreenStates.StateLooks -> LooksState(viewModel, navController)
            is ClosetScreenStates.StateClothes -> ClothesState(viewModel, navController)
            is ClosetScreenStates.StateIdle -> {}

        }
        Tag()
        DownButton(
            onClick = {
                when (state.value) {
                    is ClosetScreenStates.StateLooks -> {}
                    is ClosetScreenStates.StateClothes -> viewModel.showAddClothes()
                    is ClosetScreenStates.StateIdle -> {}
                }
            }
        )
    }
    if (showAddClothes.value) {
        //AddClothes(/*onClose = { viewModel.closeAddClothes() }*/)
    }

}

@Composable
fun ClothesState(viewModel: ClosetScreenViewModel, navController: NavController) {
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
                onClick = {  }
            ) {
                Icon(imageVector = FilterIC, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
            IconButton(
                onClick = { navController.navigate("search_screen") }
            ){
                Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
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
                onClick = {  }
            ) {
                Icon(imageVector = FilterIC, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
            IconButton(
                onClick = { navController.navigate("search_screen") }
            ){
                Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
        }
        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp,
        )
    }

}

@Composable
fun DownButton(onClick: () -> Unit) {

    //Низ
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        //Затемнение
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

        //Кнопка
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .shadow(8.dp, CircleShape), // Тень для эффекта левитации
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
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Closet",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        IconButton(
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
fun Tag() {
    //Теги
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)

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
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                )
                Text(
                    text = "Sport Style",
                    modifier = Modifier.padding(2.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
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
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                )
                Text(
                    text = "White",
                    modifier = Modifier.padding(2.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

