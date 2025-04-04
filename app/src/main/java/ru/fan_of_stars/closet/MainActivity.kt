package ru.fan_of_stars.closet


import AppTheme
import ru.fan_of_stars.closet.ui.theme.*
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import ru.fan_of_stars.closet.ui.login.LogScreen
import ru.fan_of_stars.closet.ui.registration.RegScreen
import androidx.navigation.compose.*
import ru.fan_of_stars.closet.ui.closet.ClosetScreen
import ru.fan_of_stars.closet.ui.closet.ClosetScreenViewModel
import ru.fan_of_stars.closet.ui.search.SearchHistoryManager
import ru.fan_of_stars.closet.ui.search.SearchScreen
import ru.fan_of_stars.closet.ui.settings.SettingsScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
            val historyManager = SearchHistoryManager(this)

            AppTheme(darkTheme = isDarkTheme) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        innerPadding,
                        themeViewModel,
                        historyManager
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigation(paddingValues: PaddingValues, themeViewModel: ThemeViewModel, historyManager: SearchHistoryManager) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "closet_screen") {
        composable("reg_screen") { RegScreen(navController, paddingValues) }
        composable("log_screen") { LogScreen(navController, paddingValues) }
        composable("closet_screen") {
            ClosetScreen(
                navController,
                ClosetScreenViewModel(),
                paddingValues
            )
        }
        composable("settings_screen") { SettingsScreen(paddingValues, themeViewModel) }
        composable("search_screen") { SearchScreen(historyManager, paddingValues) }
    }
}

