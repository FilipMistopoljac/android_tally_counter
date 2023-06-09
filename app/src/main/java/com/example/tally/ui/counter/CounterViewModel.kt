package com.example.tally.ui.counter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tally.data.model.dao.Counter
import com.example.tally.data.model.dao.Event
import com.example.tally.data.model.schema.Counters
import kotlinx.coroutines.launch
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.transactions.transaction

class CounterViewModel(
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

    fun count() {
        state {
            Event.new {counter = state.counter}
            all()
        }
    }

    fun update(statement: () -> Unit) {
        state {
            statement()
            all()
        }
    }
}