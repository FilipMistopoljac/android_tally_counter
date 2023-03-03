@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.example.tally.ui.counter

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tally.data.model.dao.Event
import com.example.tally.data.model.toInstant
import com.example.tally.data.model.toLocalDateTime
import com.example.tally.ui.theme.Typography
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import kotlinx.datetime.DateTimeUnit.Companion.HOUR
import kotlinx.datetime.DateTimeUnit.Companion.MINUTE
import kotlin.properties.Delegates


@Composable
fun CounterScreen(
    vm: CounterViewModel,
) {
    var open by remember { mutableStateOf(false) }
    var event: Event by Delegates.notNull();

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "${vm.state.counter.name}", maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            },
        )
    }, floatingActionButton = {
        LargeFloatingActionButton(onClick = vm::count) {
            Icon(Icons.Default.Add, null)
        }
    }

    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {

            EditDialog(
                event = event,
                onConfirm = {},
                onDismiss = { open = false }
            )

            LazyColumn {
                items(vm.state.counter.events.count().toInt()) {
                    val element = vm.state.counter.events.elementAt(it)
                    EventCard(vm.state.counter.events.elementAt(it), onClick = {
                        event = element
                        open = true
                    })
                }
            }
        }
    }
}


@Composable
fun EventCard(event: Event, onClick: (Event) -> Unit) {
    Card(onClick = { onClick(event) }) {
        Text("${event.timestamp}")
    }
}


@Composable
fun EditDialog(
    event: Event,
    onConfirm: (LocalDateTime) -> Unit,
    onDismiss: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    val (datePickerState, timePickerState) = remember(event.timestamp) {
        toDateTimeStates(event.timestamp)
    }

    DatePickerDialog(
        confirmButton = {
            when (pagerState.currentPage) {
                0 -> Button(onClick = { coroutineScope.launch { pagerState.animateScrollToPage(1) } }) {
                    Text(text = "Next")
                }
                1 -> Button(onClick = {
                    onConfirm(toLocalDateTime(datePickerState, timePickerState))
                        .also { onDismiss() }
                }) {
                    Text(text = "Confirm")
                }
            }
        },
        dismissButton = {
            when (pagerState.currentPage) {
                0 -> Button(onClick = onDismiss) {
                    Text(text = "Dismiss")
                }
                1 -> Button(onClick = { coroutineScope.launch { pagerState.animateScrollToPage(0) } }) {
                    Text("Black")
                }
            }
        },
        onDismissRequest = {}
    ) {
        HorizontalPager(
            pageCount = 2,
            state = pagerState,
            modifier = Modifier.requiredHeight(500.dp)
        ) {
            Column {
                when (it) {
                    0 -> {
                        Text(
                            text = "Selected time",
                            style = Typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(120.dp)
                                .padding(PaddingValues(24.dp, 16.dp, 32.dp, 12.dp))
                        )
                        Divider()
                        Spacer(modifier = Modifier.height(20.dp))
                        TimePicker(
                            state = timePickerState, modifier = Modifier.fillMaxWidth()
                        )
                    }

                    1 -> {
                        DatePicker(
                            state = datePickerState, title = null, showModeToggle = false
                        )
                    }
                }
                Spacer(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

fun toDateTimeStates(timestamp: LocalDateTime) =

    with(timestamp.toInstant().toEpochMilliseconds()) {
        DatePickerState(
            initialSelectedDateMillis = this,
            initialDisplayedMonthMillis = this,
            yearRange = DatePickerDefaults.YearRange,
            initialDisplayMode = DisplayMode.Picker
        )
    } to with(timestamp) {
        TimePickerState(
            initialHour = hour,
            initialMinute = minute,
            is24Hour = true
        )
    }

fun toLocalDateTime(datePickerState: DatePickerState, timePickerState: TimePickerState) =
    (datePickerState.selectedDateMillis
        ?.let {
            Instant.fromEpochMilliseconds(it)
                .plus(timePickerState.hour, HOUR)
                .plus(timePickerState.minute, MINUTE)
        }
        ?: Clock.System.now()
            ).toLocalDateTime()


