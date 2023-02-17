package com.example.tally.ui.main

import com.example.tally.data.model.dao.Counter

data class MainState(val counters: Iterable<Counter>) {
}