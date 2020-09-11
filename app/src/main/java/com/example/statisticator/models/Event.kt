package com.example.statisticator.models

import java.io.Serializable
import java.util.*

class Event : Serializable {
    val id: String
    val model: EventModel
    val timestamp: String
    val attributes: MutableMap<String, Serializable> = mutableMapOf()

    constructor(model: EventModel, timestamp: String) {
        this.id = UUID.randomUUID().toString()
        this.model = model
        this.timestamp = timestamp
    }
}

