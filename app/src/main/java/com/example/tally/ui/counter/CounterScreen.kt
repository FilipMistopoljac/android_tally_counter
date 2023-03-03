package com.example.tally.ui.counter

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.example.tally.data.model.dao.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterScreen(
    vm: CounterViewModel,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "${vm.state.counter?.name}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(onClick = vm::count) {
                Icon(Icons.Default.Add, null)
            }
        }

    ) { paddingValues ->
       Box(modifier = Modifier.padding(paddingValues) ) {
           LazyColumn {
               items(vm.state.counter.events.count().toInt()) {
                EventCard(vm.state.counter.events.elementAt(it), onClick = {/*TODO*/})
               }
           }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCard(event: Event, onClick: (Event) -> Unit) {
    Card(onClick = { onClick(event)}) {
        Text("${event.timestamp}")
    }
}

@Composable
fun EditDialog() {
//    DatePickerDialog
//    HorizontalPager
//    DatePicker

}