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
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tally.data.model.configureDatabase
import com.example.tally.ui.counter.CounterScreen
import com.example.tally.ui.counter.CounterViewModel
import com.example.tally.ui.main.MainScreen
import com.example.tally.ui.main.MainViewModel
import com.example.tally.ui.screens.Navigation
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
                    com.example.tally.ui.screens.Navigation()
                }
            }
        }
    }
}

