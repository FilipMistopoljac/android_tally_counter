package com.example.tally.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.tally.data.model.dao.Counter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    vm: MainViewModel
) {
    var open by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Add") },
                icon = { Icon(Icons.Filled.Add, null) },
                onClick = { open = true }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            CounterDialog(
                open = open,
                onConfirm = {
                    vm.new(it)
                    open = false
                },
                onDismiss = { open = false }
            )

            LazyColumn {
                items(vm.state.counters) {
                    Text(it.name)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterDialog(open: Boolean, onConfirm: (String) -> Unit, onDismiss: () -> Unit) {
    var name by remember { mutableStateOf("") }

    if (open) AlertDialog(
        icon = { Icon(Icons.Filled.Create, null) },
        title = { Text("Add new counter") },
        text = {
            OutlinedTextField(
                label = { Text("Name") },
                value = name,
                onValueChange = { name = it },
                singleLine = true
            )
        },
        confirmButton = {
            Button(onClick = { onConfirm(name).also { name = "" } }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss. also { name = "" }) {
                Text(text = "Dismiss")
            }
        },
        onDismissRequest = onDismiss.also { name = "" }
    )
}