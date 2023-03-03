package com.example.tally.data.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.toInstant

fun Instant.toLocalDateTime() = toLocalDateTime(TimeZone.currentSystemDefault())

fun LocalDateTime.toInstant() = toInstant(TimeZone.currentSystemDefault())