package ru.fan_of_stars.closet.ui.registration

import AppTheme
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import darkScheme
import lightScheme
import ru.fan_of_stars.closet.ui.theme.*


@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun RegScreen() {
    val colors = if (isSystemInDarkTheme()) darkScheme else lightScheme
    with(colors) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Create your Closet!",
                fontSize = 24.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = onSurface,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )

            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Your phone*") },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()

            )
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Your name*") },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()

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

            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Repeat password*") },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()

            )

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(alignment = androidx.compose.ui.Alignment.CenterHorizontally)
            ) {
                Text("Open Closet!")
            }

            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(alignment = androidx.compose.ui.Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Already have an account?\nSign in!",
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }

}