package com.example.statisticator.models.attributes

import com.example.statisticator.models.Event
import java.io.Serializable

interface EventAttribute: Serializable {
    val id: String
}

interface EditableAttribute: EventAttribute {
    val title: String
}

interface CalculatableAttribute: EventAttribute {
    fun calculateFor(event: Event)
}