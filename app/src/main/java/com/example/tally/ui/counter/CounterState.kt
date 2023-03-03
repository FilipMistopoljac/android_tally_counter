package com.example.tally.ui.counter

import com.example.tally.data.model.dao.Counter
import com.example.tally.data.model.dao.Event

data class CounterState(val id: Int, val counter: Counter, val events: List<Event>)