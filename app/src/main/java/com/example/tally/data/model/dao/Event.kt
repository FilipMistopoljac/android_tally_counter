package com.example.tally.data.model.dao

import com.example.tally.data.model.schema.Events
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Event(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Event>(Events)

    val timestamp by Events.timestamp
    val counter by Counter referencedOn Events.counter
}