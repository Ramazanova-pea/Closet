package ru.fan_of_stars.closet.ui.closet

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import ru.fan_of_stars.closet.ui.icons.AddAPhoto
import ru.fan_of_stars.closet.ui.icons.CheckIC
import ru.fan_of_stars.closet.ui.icons.ImageIcon
import ru.fan_of_stars.closet.ui.theme.onSurfaceLight
import ru.fan_of_stars.closet.ui.theme.onTertiaryContainerLight
import ru.fan_of_stars.closet.ui.theme.primaryContainerLight
import ru.fan_of_stars.closet.ui.theme.surfaceContainerHighLight
import ru.fan_of_stars.closet.ui.theme.surfaceContainerHighestLight
import ru.fan_of_stars.closet.ui.theme.surfaceContainerLight
import ru.fan_of_stars.closet.ui.theme.surfaceContainerLowLight
import ru.fan_of_stars.closet.ui.theme.tertiaryContainerLight
import ru.fan_of_stars.closet.ui.theme.tertiaryContainerLightHighContrast


@Composable
fun AddClothes(onClose: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(surfaceContainerLight)
        ){
            Column (
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text="Add new clothes",
                    color = onSurfaceLight,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp)
                )

                Row {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .border(2.dp, tertiaryContainerLightHighContrast, RoundedCornerShape(16.dp) )
                            .background(surfaceContainerHighLight)
                            .padding(16.dp)

                    ){
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = AddAPhoto,
                                contentDescription = null,
                                tint = onTertiaryContainerLight,
                            )
                            Text(
                                text = "Camera",
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Box(
                        contentAlignment = androidx.compose.ui.Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .border(2.dp, tertiaryContainerLightHighContrast, RoundedCornerShape(16.dp) )
                            .background(surfaceContainerHighLight)
                            .padding(16.dp)

                    ){
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = ImageIcon,
                                contentDescription = null,
                                tint = onTertiaryContainerLight,
                            )
                            Text(
                                text = "Gallery",
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }



                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(primaryContainerLight)
                ) {
                    Icon(
                        imageVector = CheckIC,
                        contentDescription = null,
                        tint = onSurfaceLight,
                        modifier = Modifier.padding(8.dp)
                    )
                }

            }
        }
    }

}

