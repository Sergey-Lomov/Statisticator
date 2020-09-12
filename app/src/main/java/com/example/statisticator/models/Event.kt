package com.example.statisticator.models

import com.example.statisticator.constants.Constants
import com.example.statisticator.models.schema.EventModel
import com.example.statisticator.models.schema.modificators.SessionStateModificator
import java.io.Serializable
import java.util.*

class Event : Serializable {
    var id: String
        get() = { (attributes[Constants.EVENT_ID_KEY] as? String) ?: "" }()
        set(value) { attributes[Constants.EVENT_ID_KEY] = value }

    var timestamp: String
        get() = { (attributes[Constants.EVENT_TIMESTAMP_KEY] as? String) ?: "" }()
        set(value) { attributes[Constants.EVENT_TIMESTAMP_KEY] = value }

    var type: String
        get() = { (attributes[Constants.EVENT_TYPE_KEY] as? String) ?: "" }()
        set(value) { attributes[Constants.EVENT_TYPE_KEY] = value }

    val model: EventModel
    val attributes: MutableMap<String, Serializable> = mutableMapOf()

    constructor(model: EventModel, timestamp: String) {
        this.id = UUID.randomUUID().toString()
        this.type = model.type
        this.timestamp = timestamp
        this.model = model
    }
}

