package com.example.tally.data.model.schema

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object Events : IntIdTable() {
    val timestamp = timestamp("timestamp")
    val counter = reference("counter", Counters)
}