package com.example.tally.data.model.schema

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object Events : IntIdTable() {
    val timestamp = timestamp("timestamp").defaultExpression(CurrentTimestamp())
    val counter = reference("counter", Counters)
}