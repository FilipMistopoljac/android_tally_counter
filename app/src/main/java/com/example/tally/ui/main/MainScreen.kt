package com.example.tally.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    vm: MainViewModel
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Add")},
                icon = { Icon(Icons.Filled.Add, null)},
                onClick = { /*TODO*/ }
            )
        }
    ) {paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {

        }
    }
}