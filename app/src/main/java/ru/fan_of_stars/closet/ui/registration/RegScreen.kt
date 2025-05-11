package ru.fan_of_stars.closet.ui.registration

import AppTheme
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: RegScreenViewModel = koinViewModel(),
) {
    val state = viewModel.state
    val context = LocalContext.current

    // Показываем Toast и навигируем, только если регистрация успешна
    LaunchedEffect(state.token) {
        state.token?.let {
            Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
            navController.navigate("closet_screen") {
                popUpTo("registration_screen") { inclusive = true }
            }
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let {
            Toast.makeText(context, "Registration failed: $it", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Create your Closet!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )

        TextField(
            value = state.login,
            onValueChange = { viewModel.OnEvent(RegistrationEvent.OnLoginChange(it)) },
            label = { Text("Your login*") },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 32.dp)
                .fillMaxWidth()

        )
        TextField(
            value = state.username,
            onValueChange = { viewModel.OnEvent(RegistrationEvent.OnUsernameChange(it)) },
            label = { Text("Your username*") },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 32.dp)
                .fillMaxWidth()

        )

        TextField(
            value = state.email,
            onValueChange = { viewModel.OnEvent(RegistrationEvent.OnEmailChange(it)) },
            label = { Text("E-mail*") },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 32.dp)
                .fillMaxWidth()

        )

        TextField(
            value = state.password,
            onValueChange = { viewModel.OnEvent(RegistrationEvent.OnPasswordChange(it)) },
            label = { Text("Password*") },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 32.dp)
                .fillMaxWidth()

        )

        Button(
            onClick = {
                Log.d("RegScreen", "Button clicked")
                viewModel.OnEvent(RegistrationEvent.Submit)
            },
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Open Closet!")
        }

        TextButton(
            onClick = { navController.navigate("log_screen") },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Already have an account?\nSign in!",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
    // Индикатор загрузки поверх формы
    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)) // затемнение
                .clickable(enabled = false) {}              // блокировка взаимодействия
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}