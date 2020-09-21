package com.example.statisticator.models.schema.attributes

import com.example.statisticator.models.Event
import com.example.statisticator.service.LoggingState
import java.io.Serializable

interface Attribute: Serializable {
    val id: String
}

interface EditableAttribute: Attribute {
    val title: String?
}

interface CalculatableAttribute: Attribute {
    fun calculateFor(event: Event, state: LoggingState): Serializable?
}