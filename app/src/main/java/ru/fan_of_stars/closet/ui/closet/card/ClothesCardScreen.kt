package ru.fan_of_stars.closet.ui.closet.card

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.fan_of_stars.closet.ui.closet.Tag

@Preview
@Composable
fun ClothesCardScreen() {
    var isSelected by remember { mutableStateOf(false) }
    val content = LocalContext.current
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
        label = "BorderColorAnimation"
    )
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 0.95f else 1f,
        label = "ScaleAnimation"
    )

    Box(
        modifier = Modifier
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (isSelected) isSelected = !isSelected
                        else {
                            Toast.makeText(content, "Обычное нажатие", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onLongPress = {
                        // долгое нажатие
                        isSelected = !isSelected
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
                Column(
                ) {
                    // Картинка
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .background(
                                MaterialTheme.colorScheme.surfaceContainer,
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Item",
                            modifier = Modifier.size(64.dp)
                        )
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        text = "Name",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }
            }
        }

        // Индикатор в верхнем левом углу
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