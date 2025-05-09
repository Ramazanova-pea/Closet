package ru.fan_of_stars.closet.ui.login

import AppTheme
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.fan_of_stars.closet.ui.theme.*

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun show(){
    LogScreen(
        navController = rememberNavController(),
        paddingValues = PaddingValues(16.dp)
    )
}

@Composable
fun LogScreen(
    navController: NavController,
    paddingValues: PaddingValues
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Open your Closet!",
            fontSize = 24.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )

        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Your login*") },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 32.dp)
                .fillMaxWidth(),


            )

        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Password*") },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 32.dp)
                .fillMaxWidth()

        )

        Button(
            onClick = { navController.navigate("closet_screen") },
            modifier = Modifier
                .align(alignment = androidx.compose.ui.Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )

        ) {
            Text("Open Closet!")
        }

        TextButton(
            onClick = { navController.navigate("reg_screen") },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = androidx.compose.ui.Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Don't have an account?\nLog in!",
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

}