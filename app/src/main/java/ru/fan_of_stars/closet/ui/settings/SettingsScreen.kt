package ru.fan_of_stars.closet.ui.settings

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fanofstars.domain.usecases.GetUserByTokenUseCase
import org.koin.androidx.compose.koinViewModel
import ru.fan_of_stars.closet.ThemeViewModel
import ru.fan_of_stars.closet.ui.user.UserState
import ru.fan_of_stars.closet.ui.user.UserViewModel

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun SettingsScreenPreview() {
    //SettingsScreen(paddingValues = PaddingValues(16.dp), themeViewModel: ThemeViewModel = viewModel())
}

@Composable
fun SettingsScreen(
    paddingValues: PaddingValues,
    themeViewModel: ThemeViewModel,
    userViewModel: UserViewModel = koinViewModel()
) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val userState by userViewModel.userState.collectAsState()

    // Загружаем данные при открытии экрана
    LaunchedEffect(Unit) {
        userViewModel.loadUserData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()
        PersonalSetting(userState)
        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp,
        )
        AppSetting(isDarkTheme) {
            themeViewModel.switchTheme()
        }
    }
}

@Composable
fun AppSetting(isDarkTheme: Boolean, onSwitchTheme: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                text = "Dark theme",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Switch(
                checked = isDarkTheme,
                onCheckedChange = {
                    onSwitchTheme()
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                    uncheckedThumbColor = MaterialTheme.colorScheme.primary,
                    uncheckedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )
        }
    }
}

@Composable
fun PersonalSetting(userState: UserState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        when (userState) {
            is UserState.Loading -> {
                Text(
                    text = "Loading user data...",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            is UserState.Error -> {
                Text(
                    text = "Error: ${userState.message}",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.error,
                )
            }
            is UserState.Success -> {
                val user = userState.user
                Text(
                    text = "Login: ${user.login}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "E-mail: ${user.email ?: "-"}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "Username: ${user.username}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "Password: hidden", // Пароль лучше не показывать
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}


@Composable
fun Header() {
    //Заголовок
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Settings",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
    }
}