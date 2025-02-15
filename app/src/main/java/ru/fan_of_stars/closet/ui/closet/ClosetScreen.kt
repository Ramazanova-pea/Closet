package ru.fan_of_stars.closet.ui.closet


import AppTheme
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import ru.fan_of_stars.closet.ui.icons.CloseIC
import ru.fan_of_stars.closet.ui.icons.FilterIC
import ru.fan_of_stars.closet.ui.icons.SettingIC
import ru.fan_of_stars.closet.ui.theme.onPrimaryContainerLight
import ru.fan_of_stars.closet.ui.theme.onPrimaryLight
import ru.fan_of_stars.closet.ui.theme.onSurfaceDark
import ru.fan_of_stars.closet.ui.theme.onSurfaceLight
import ru.fan_of_stars.closet.ui.theme.outlineLight
import ru.fan_of_stars.closet.ui.theme.primaryContainerLight
import ru.fan_of_stars.closet.ui.theme.primaryLight
import ru.fan_of_stars.closet.ui.theme.surfaceContainerDark
import ru.fan_of_stars.closet.ui.theme.surfaceContainerHighDark
import ru.fan_of_stars.closet.ui.theme.surfaceContainerHighLight
import ru.fan_of_stars.closet.ui.theme.surfaceContainerLight
import ru.fan_of_stars.closet.ui.theme.surfaceContainerLowLight
import java.security.KeyStore.TrustedCertificateEntry

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun ClosetScreen(){
    AppTheme(    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Заголовок
            Row(
                modifier = Modifier
                    .background(surfaceContainerHighLight)
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Top
            ){
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
            //Выбор гардероб или образы
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
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
                        modifier = Modifier.padding(start = 8.dp)
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
            //Теги
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)

            ){
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

            //Карточки
            Box(

            ){

            }

            //Низ
            Box(
                modifier = Modifier.fillMaxSize()
            ){
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
                    onClick = {},
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
    }
}