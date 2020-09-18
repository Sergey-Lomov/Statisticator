package com.example.statisticator.models

import com.example.statisticator.constants.Constants
import com.example.statisticator.models.schema.EventModel
import java.io.Serializable
import java.util.*

class Event : Serializable {

    private val idKey = Constants.EventParsingKeys.Id.value
    private val timestampKey = Constants.EventParsingKeys.Timestamp.value
    private val typeKey = Constants.EventParsingKeys.Type.value

    var id: String
        get() = { (attributes[idKey] as? String) ?: "" }()
        set(value) { attributes[idKey] = value }

    var timestamp: String
        get() = { (attributes[timestampKey] as? String) ?: "" }()
        set(value) { attributes[timestampKey] = value }

    var type: String
        get() = { (attributes[typeKey] as? String) ?: "" }()
        set(value) { attributes[typeKey] = value }

    val model: EventModel
    val attributes: MutableMap<String, Serializable>
        get() = attributesContainer.values
    val attributesContainer: AttributesContainer

    constructor(model: EventModel, timestamp: String) {
        this.attributesContainer = AttributesContainer()
        this.id = UUID.randomUUID().toString()
        this.type = model.type
        this.timestamp = timestamp
        this.model = model
    }

    constructor(model: EventModel, attributesContainer: AttributesContainer) {
        this.model = model
        this.attributesContainer = attributesContainer
    }

}

