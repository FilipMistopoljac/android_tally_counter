package com.example.tally.ui.counter

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CounterScreen(
    vm: CounterViewModel,
    id: Int?
) {
    Text(text = "Hi $id")
}