package ru.fan_of_stars.closet.ui.closet.card

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.fanofstars.domain.model.Item
import org.koin.androidx.compose.koinViewModel

@Composable
fun ClothesCardScreen(
    navController: NavController,
    item: Item,
    selectedItems: MutableList<String>? = null
) {
    var isSelected = selectedItems?.contains(item.name) ?: false

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
        label = "BorderColorAnimation"
    )
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 0.95f else 1f,
        label = "ScaleAnimation"
    )

    val itemViewModel: ItemViewModel = koinViewModel()

    Box(
        modifier = Modifier
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (selectedItems != null) {
                            if (isSelected) {
                                selectedItems.remove(item.name)
                                isSelected = !isSelected
                            } else {
                                itemViewModel.setItem(item)
                                navController.navigate("full_item_screen")
                            }
                        } else {
                            itemViewModel.setItem(item)
                            navController.navigate("full_item_screen")
                        }
                    },
                    onLongPress = {
                        if (selectedItems != null) {
                            if (isSelected) {
                                selectedItems.remove(item.name)
                                isSelected = !isSelected
                            } else {
                                selectedItems.add(item.name)
                                isSelected = !isSelected
                            }
                        }
                    }
                )
            }
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(2.dp, borderColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box() {
                Column {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .background(MaterialTheme.colorScheme.surfaceContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        if (item.imagePath.isEmpty()) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp)
                            )
                        } else {
                            AsyncImage(
                                model = item.imagePath,
                                contentDescription = item.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .align(Alignment.TopStart)
                    .offset(x = 8.dp, y = 8.dp)
            )
        }
    }
}
