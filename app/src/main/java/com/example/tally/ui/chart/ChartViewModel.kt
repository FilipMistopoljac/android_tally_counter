package com.example.tally.ui.chart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tally.data.model.dao.Counter
import com.example.tally.data.model.schema.Counters
import com.example.tally.ui.counter.CounterState
import kotlinx.coroutines.launch
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.transactions.transaction

class ChartViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: Int = savedStateHandle["id"] ?: -1

    var state by mutableStateOf(CounterState(id, Counter(EntityID(id, Counters)), emptyList()))
        private set

    private fun state(block: CounterState.() -> CounterState) {
        viewModelScope.launch {
            transaction {
                state = state.block()
            }
        }
    }

    init {
        state { all() }
    }

    private fun CounterState.all() = copy(
        counter = Counter[id].load(Counter::events),
        events = Counter[id].events.sortedBy { it.timestamp }
    )

}