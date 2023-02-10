package com.example.tally.data.model.dao


import com.example.tally.data.model.schema.Counters
import com.example.tally.data.model.schema.Events
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Counter(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Counter>(Counters)

    var name by Counters.name
    val events by Event referrersOn Events.counter
}