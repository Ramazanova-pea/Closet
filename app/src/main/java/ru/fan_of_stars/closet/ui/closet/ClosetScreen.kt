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
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import ru.fan_of_stars.closet.ui.icons.*
import ru.fan_of_stars.closet.ui.theme.*

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun ClosetScreen(viewModel: ClosetScreenViewModel = ClosetScreenViewModel()) {
    val state = viewModel.state.collectAsState()
//    val state = ClosetScreenStates.StateLooks
    val showAddClothes = remember { mutableStateOf(viewModel.showAddClothes) }


    AppTheme()
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(
                    WindowInsets.statusBars.only(WindowInsetsSides.Top)
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Header()
            when (state.value) {
                is ClosetScreenStates.StateLooks -> LooksState(viewModel)
                is ClosetScreenStates.StateClothes -> ClothesState(viewModel)
                is ClosetScreenStates.StateIdle -> {}

            }
            Tag()
            DownButton(
                onClick = {
                    when(state.value) {
                        is ClosetScreenStates.StateLooks -> {}
                        is ClosetScreenStates.StateClothes -> viewModel.showAddClothes()
                        is ClosetScreenStates.StateIdle -> {}
                    }
                }
            )
        }
        if (showAddClothes.value) {
            AddClothes(onClose = { viewModel.closeAddClothes() })
        }
    }
}

@Composable
fun ClothesState(viewModel: ClosetScreenViewModel) {
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
                color = primaryLight,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Looks",
                fontSize = 16.sp,
                color = onSurfaceLight,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable { viewModel.onLooksClick() }
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            Image(
                FilterIC,
                contentDescription = null,
                colorFilter = ColorFilter.tint(primaryLight),
            )
        }
        Divider(
            color = primaryLight,
            thickness = 2.dp,
        )
    }
}


@Composable
fun LooksState(viewModel: ClosetScreenViewModel) {
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
                color = onSurfaceLight,
                modifier = Modifier.clickable { viewModel.onClothesClick() }
            )
            Text(
                text = "Looks",
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp),
                color = primaryLight,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            Image(
                FilterIC,
                contentDescription = null,
                colorFilter = ColorFilter.tint(primaryLight),
            )
        }
        Divider(
            color = primaryLight,
            thickness = 2.dp,
        )
    }

}

@Composable
fun DownButton(onClick: () -> Unit ) {

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
                            outlineLight
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
            containerColor = primaryLight,
            contentColor = onPrimaryLight
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
fun Header(){
    //Заголовок
    Row(
        modifier = Modifier
            .background(surfaceContainerHighLight)
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "Closet",
            fontSize = 24.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            color = onSurfaceLight,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Image(
            SettingIC,
            contentDescription = null,
            modifier = Modifier.wrapContentWidth()
        )
    }
}

@Composable
fun Tag(){
    //Теги
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)

    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(primaryContainerLight)
                .padding(start = 8.dp, end = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    CloseIC,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(onPrimaryContainerLight)
                )
                Text(
                    text = "Sport Style",
                    modifier = Modifier.padding(2.dp),
                    color = onPrimaryContainerLight
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(primaryContainerLight)
                .padding(start = 8.dp, end = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    CloseIC,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(onPrimaryContainerLight)
                )
                Text(
                    text = "White",
                    modifier = Modifier.padding(2.dp),
                    color = onPrimaryContainerLight
                )
            }
        }
    }
}

