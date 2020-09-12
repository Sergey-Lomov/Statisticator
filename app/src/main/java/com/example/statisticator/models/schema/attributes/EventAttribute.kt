package com.example.statisticator.models.schema.attributes

import com.example.statisticator.models.Event
import com.example.statisticator.models.SessionState
import java.io.Serializable

interface EventAttribute: Serializable {
    val id: String
}

interface EditableAttribute: EventAttribute {
    val title: String
}

interface CalculatableAttribute: EventAttribute {
    fun calculateFor(event: Event, state: SessionState): Serializable?
}