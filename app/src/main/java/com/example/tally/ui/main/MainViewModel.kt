package com.example.tally.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tally.data.model.dao.Counter
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.transactions.transaction

class MainViewModel : ViewModel() {
    var state: MainState by mutableStateOf(MainState(emptyList()))
        private set

    init {
        all()
    }

    fun bla() {
        Counter.all().asFlow()
    }

    fun all() {
        viewModelScope.launch {
            state = state.copy(counters = transaction {Counter.all().toList()} )
        }
    }

    fun new(name: String) {
        viewModelScope.launch {
            transaction {
                Counter.new { this.name = name }
            }
        }
        all()
    }
}