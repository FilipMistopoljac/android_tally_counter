package com.example.tally.data.model

import com.example.tally.data.model.schema.Counters
import com.example.tally.data.model.schema.Events
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun configureDatabase() {
    Database.connect(
        url = "jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;",
        driver = "org.h2.Driver"
    )
    transaction {
        SchemaUtils.createMissingTablesAndColumns(Counters, Events)
    }
}