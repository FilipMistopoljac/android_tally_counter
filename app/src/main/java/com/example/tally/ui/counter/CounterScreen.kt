@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.example.tally.ui.counter

import android.graphics.Paint.Align
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tally.R
import com.example.tally.data.model.dao.Event
import com.example.tally.data.model.toInstant
import com.example.tally.data.model.toLocalDateTime
import com.example.tally.ui.theme.Typography
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import kotlinx.datetime.DateTimeUnit.Companion.HOUR
import kotlinx.datetime.DateTimeUnit.Companion.MINUTE
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates


@Composable
fun CounterScreen(
    vm: CounterViewModel,
    navController: NavController
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
            actions = {
                IconButton(onClick = { navController.navigate("chart/${vm.state.id}") }) {
                    Icon(
                        painterResource(R.drawable.table_chart_view_fill0_wght400_grad0_opsz48),
                        null,
                        modifier = Modifier.fillMaxSize(0.75f)
                    )
                }
            }
        )
    }, floatingActionButton = {
        LargeFloatingActionButton(onClick = vm::count) {
            Icon(Icons.Default.Add, null)
        }
    }

    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {

            if (open) EditDialog(
                event = event,
                onConfirm = { vm.update { event.timestamp = it } },
                onDismiss = { open = false }
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                state = rememberLazyGridState(
                    (vm.state.events.size - 1).coerceAtLeast(0)
                ),
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                vm.state.events.groupBy { it.timestamp.date }.forEach { date, events ->
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            ProvideTextStyle(MaterialTheme.typography.titleLarge) {
                                Text("${date.dayOfWeek.name
                                    .lowercase()
                                }, $date")
                            }
                        }
                    }
                    items(events) {
                        EventCard(it, onClick = {
                            event = it
                            open = true
                        })
                    }
                }
            }
        }
    }
}

val formatter = DateTimeFormatter.ofPattern("HH:mm")

@Composable
fun EventCard(event: Event, onClick: (Event) -> Unit) {
    Card(onClick = { onClick(event) }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                event.timestamp.toJavaLocalDateTime().format(formatter)
            )
        }


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
            is24Hour = false
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


