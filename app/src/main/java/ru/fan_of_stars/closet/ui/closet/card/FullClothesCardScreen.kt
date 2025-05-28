package ru.fan_of_stars.closet.ui.closet.card

import com.fanofstars.domain.model.Item
import ru.fan_of_stars.closet.ui.closet.addItem.AddItemViewModel


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import ru.fan_of_stars.closet.ui.closet.Tag

@Composable
fun FullClothesCardScreen(
    navController: NavHostController,
    item: Item,
    viewModel: AddItemViewModel = koinViewModel()
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { navController.popBackStack() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
                .align(Alignment.Center)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { }
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                val imagePicker = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent(),
                    onResult = { uri: Uri? ->
                        if (uri != null) {
                            viewModel.onImageSelected(uri)
                        }
                    }
                )

                // Картинка (заглушка)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                        .clickable {
                            imagePicker.launch("image/*")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (item.imagePath != null) {
                        // Показываем фото
                        AsyncImage( // Используй Coil для загрузки Uri
                            model = item.imagePath,
                            contentDescription = "Selected Item",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        // Заглушка
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Item",
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }

                Text(
                    text = item.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                // Выбранные тэги
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(item.tags) { tag ->
                        Tag(text = tag) {
                            // здесь можно обработать клик по тэгу, если нужно
                        }
                    }
                }


                // Поле заметок
                Text(
                    text = item.notes ?: "Notes...",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

