package com.example.statisticator.models

import java.io.Serializable
import java.sql.Timestamp

class Event : Serializable {
    val model: EventModel
    val timestamp: Timestamp
    val attributes: MutableMap<String, Serializable> = mutableMapOf()

    constructor(model: EventModel, timestamp: Timestamp) {
        this.model = model
        this.timestamp = timestamp
    }
}

