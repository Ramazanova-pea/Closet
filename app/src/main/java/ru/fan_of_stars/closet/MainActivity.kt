package ru.fan_of_stars.closet


import AppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import ru.fan_of_stars.closet.ui.login.LogScreen
import ru.fan_of_stars.closet.ui.registration.RegScreen
import androidx.navigation.compose.*
import org.koin.androidx.compose.koinViewModel
import ru.fan_of_stars.closet.ui.closet.ClosetScreen
import ru.fan_of_stars.closet.ui.closet.ClosetScreenViewModel
import ru.fan_of_stars.closet.ui.closet.addItem.AddItemScreen
import ru.fan_of_stars.closet.ui.closet.card.FullClothesCardScreen
import ru.fan_of_stars.closet.ui.closet.card.ItemViewModel
import ru.fan_of_stars.closet.ui.filter.FilterScreen
import ru.fan_of_stars.closet.ui.registration.RegScreenViewModel
import ru.fan_of_stars.closet.ui.search.SearchHistoryManager
import ru.fan_of_stars.closet.ui.search.SearchScreen
import ru.fan_of_stars.closet.ui.settings.SettingsScreen
import androidx.lifecycle.viewmodel.compose.viewModel as viewModel1


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val themeViewModel: ThemeViewModel = viewModel1()
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
    NavHost(navController, startDestination = "log_screen") {
        composable("reg_screen") {
            RegScreen(navController, paddingValues, )
        }
        composable("log_screen") { LogScreen(navController, paddingValues) }
        composable("closet_screen") {
            ClosetScreen(
                navController,
                paddingValues,

            )
        }
        composable("settings_screen") { SettingsScreen(paddingValues, themeViewModel, ) }
        composable("search_screen") { SearchScreen(navController, historyManager, paddingValues) }
        composable("add_item_screen") { AddItemScreen(navController) }
        composable("filter_screen") { FilterScreen(navController) }
        composable("full_item_screen") {
            val itemViewModel: ItemViewModel = koinViewModel()
            val item = itemViewModel.selectedItem.value

            if (item != null) {
                FullClothesCardScreen(
                    navController = navController,
                    item = item
                )
            }
        }
    }
}


