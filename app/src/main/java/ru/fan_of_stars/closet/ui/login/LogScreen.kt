package ru.fan_of_stars.closet.ui.login

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.fan_of_stars.closet.ui.theme.*

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun ShowPreview() {
    LogScreen(
        navController = rememberNavController(),
        paddingValues = PaddingValues(16.dp)
    )
}

@Composable
fun LogScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state = viewModel.state

    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    }

    // Если токен получен — перейти на главный экран
    LaunchedEffect(state.token) {
        if (state.token != null) {
            state.token?.let { token ->
                sharedPreferences.edit()
                    .putString("token", token)
                    .apply()
            }
            navController.navigate("closet_screen") {
                popUpTo("login_screen") { inclusive = true }
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Open your Closet!",
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
            onValueChange = { viewModel.onEvent(LoginEvent.OnLoginChange(it)) },
            label = { Text("Your login*") },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
        )

        TextField(
            value = state.password,
            onValueChange = { viewModel.onEvent(LoginEvent.OnPasswordChange(it)) },
            label = { Text("Password*") },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
        )

        if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = { viewModel.onEvent(LoginEvent.Submit) },
            enabled = !state.isLoading,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(if (state.isLoading) "Loading..." else "Open Closet!")
        }

        TextButton(
            onClick = { navController.navigate("reg_screen") },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Don't have an account?\nRegister now!",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
