package com.example.tally.ui.chart

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.m3.style.m3ChartStyle
import com.patrykandpatrick.vico.compose.style.LocalChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf
import kotlinx.datetime.DayOfWeek

class Entry(override val x: Float, override val y: Float) : ChartEntry {
    constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())

    override fun withY(y: Float) = Entry(x, y)
}

@Composable
fun ChartScreen(
    vm: ChartViewModel
) {
    val producer = ChartEntryModelProducer(
        vm.state.events
            .groupingBy { it.timestamp.date.dayOfWeek }
            .eachCountTo(
                DayOfWeek.values()
                    .associateWith { 0 }
                    .toMutableMap())
            .map { (dayOfWeek, count) -> Entry(dayOfWeek.value, count) }
    )

    ProvideChartStyle(m3ChartStyle()) {
        Chart(
            chart = lineChart(),
            chartModelProducer = producer
        )

    }

}