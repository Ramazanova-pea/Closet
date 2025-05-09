package ru.fan_of_stars.closet.ui.registration

import AppTheme
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fanofstars.domain.usecases.RegistrationUseCase
import org.koin.compose.viewmodel.koinViewModel
import ru.fan_of_stars.closet.ui.theme.onSurfaceLight
import ru.fan_of_stars.closet.ui.theme.*


@Composable
fun RegScreen(
    viewModel: RegScreenViewModel = koinViewModel(),
    navController: NavController,
    paddingValues: PaddingValues
) {
    val state = viewModel.state

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
            value = "",
            onValueChange = { viewModel.OnEvent(RegistrationEvent.OnLoginChange(it)) },
            label = { Text("Your login*") },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 32.dp)
                .fillMaxWidth()

        )
        TextField(
            value = "",
            onValueChange = { viewModel.OnEvent(RegistrationEvent.OnUsernameChange(it)) },
            label = { Text("Your username*") },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 32.dp)
                .fillMaxWidth()

        )

        TextField(
            value = "",
            onValueChange = { viewModel.OnEvent(RegistrationEvent.OnEmailChange(it)) },
            label = { Text("E-mail*") },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 32.dp)
                .fillMaxWidth()

        )

        TextField(
            value = "",
            onValueChange = { viewModel.OnEvent(RegistrationEvent.OnPasswordChange(it)) },
            label = { Text("Password*") },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 32.dp)
                .fillMaxWidth()

        )

        Button(
            onClick = {
                viewModel.OnEvent(RegistrationEvent.Submit)
                Toast.makeText(
                    navController.context,
                    "Registration successful!",
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate("closet_screen")},
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


}