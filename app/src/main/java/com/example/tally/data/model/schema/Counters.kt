package com.example.tally.data.model.schema

import org.jetbrains.exposed.dao.id.IntIdTable

object Counters : IntIdTable() {
    val name = varchar("name", 255)
}