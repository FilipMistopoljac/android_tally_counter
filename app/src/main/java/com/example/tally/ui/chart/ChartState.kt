package com.example.tally.ui.chart

import com.example.tally.data.model.dao.Counter
import com.example.tally.data.model.dao.Event

class ChartState(val id: Int, val counter: Counter, val events: List<Event>)