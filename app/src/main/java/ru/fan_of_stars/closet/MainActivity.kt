package ru.fan_of_stars.closet

import AppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import ru.fan_of_stars.closet.ui.login.LogScreen
import ru.fan_of_stars.closet.ui.registration.RegScreen
import ru.fan_of_stars.closet.ui.theme.*
import androidx.navigation.compose.*
import ru.fan_of_stars.closet.ui.closet.ClosetScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "reg_screen") {
                    composable("reg_screen") { RegScreen(navController) }
                    composable("log_screen") { LogScreen() }
                    composable("closet_screen") { ClosetScreen() }
                }
            }
        }
    }
}

