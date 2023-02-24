package com.example.tally.ui.counter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tally.data.model.dao.Counter
import com.example.tally.ui.main.MainState
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.transactions.transaction

class CounterViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(CounterState(savedStateHandle["id"] ?: -1, null))
        private set

    private fun state(block: CounterState.() -> CounterState) {
        viewModelScope.launch {
            transaction {
                state = state.block()
            }
        }
    }

    init {
        state { copy(counter = Counter.findById(id)) }
    }
}