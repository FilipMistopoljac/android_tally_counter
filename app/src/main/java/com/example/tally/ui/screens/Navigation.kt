package com.example.tally.ui.screens

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tally.ui.counter.CounterScreen
import com.example.tally.ui.counter.CounterViewModel
import com.example.tally.ui.main.MainScreen
import com.example.tally.ui.main.MainViewModel

@Composable
fun  Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable(
            route ="main"
        ) {
            MainScreen(viewModel(), navController)
        }
        composable(
            route = "counter/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            CounterScreen(viewModel())
        }
    }
}