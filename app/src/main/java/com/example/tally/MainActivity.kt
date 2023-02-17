package com.example.tally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tally.data.model.configureDatabase
import com.example.tally.ui.counter.CounterScreen
import com.example.tally.ui.counter.CounterViewModel
import com.example.tally.ui.main.MainScreen
import com.example.tally.ui.main.MainViewModel
import com.example.tally.ui.theme.TallyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureDatabase()
        setContent {
            TallyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") {
                            val vm: MainViewModel by viewModels()
                            MainScreen(vm, navController)
                        }
                        composable(
                            route = "counter/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) {
                            val vm: CounterViewModel by viewModels()
                            CounterScreen(vm, it.arguments?.getInt("id"))
                        }
                    }
                }
            }
        }
    }
}

