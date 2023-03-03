package com.example.tally.data.model.schema

import com.example.tally.data.model.toLocalDateTime
import kotlinx.datetime.Clock
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object Events : IntIdTable() {
    val timestamp = datetime("timestamp").clientDefault { Clock.System.now().toLocalDateTime() }
    val counter = reference("counter", Counters)
}