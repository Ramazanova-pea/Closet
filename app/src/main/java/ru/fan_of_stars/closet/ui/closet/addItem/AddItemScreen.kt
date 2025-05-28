package ru.fan_of_stars.closet.ui.closet.addItem

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import ru.fan_of_stars.closet.ui.closet.Tag

@Composable
fun AddItemScreen(
    navController: NavHostController,
    viewModel: AddItemViewModel = koinViewModel()
) {
    var expanded by remember { mutableStateOf(false) }

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
                // Заголовок
                Text(
                    text = "Add Item",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )


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
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Item",
                        modifier = Modifier.size(64.dp)
                    )
                }
                // Поле ввода Name
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Dropdown для тэгов (с исправленным кликом!)
                Box {
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { expanded = !expanded }
                    ) {
                        Text("Выбрать теги")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        viewModel.allTags.forEach { tag ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Checkbox(
                                            checked = viewModel.selectedTags.contains(tag),
                                            onCheckedChange = { viewModel.toggleTag(tag) }
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(tag)
                                    }
                                },
                                onClick = { viewModel.toggleTag(tag) }
                            )
                        }
                    }
                }

                // Выбранные тэги
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(viewModel.selectedTags) { tag ->
                        Tag(text = tag) {
                            viewModel.toggleTag(tag)
                        }
                    }
                }


                // Поле заметок
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    placeholder = { Text("Notes...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )

                // Кнопка
                Button(
                    onClick = { },
                    shape = CircleShape,
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
