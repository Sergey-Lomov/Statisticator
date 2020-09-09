package com.example.statisticator.models

import java.io.Serializable

class Event : Serializable {
    val model: EventModel
    val attributes: MutableMap<String, Serializable> = mutableMapOf()

    constructor(model: EventModel) {
        this.model = model
    }
}

