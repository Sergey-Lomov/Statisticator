package com.example.statisticator.models.schema

import com.example.statisticator.models.Event
import com.example.statisticator.models.schema.attributes.Attribute
import com.example.statisticator.service.factories.TypedEntitiesOwner
import java.io.Serializable

class Query(
    override val id: String,
    val attributes: ArrayList<Attribute> = ArrayList()
) : TypedEntitiesOwner {
    fun execute(events: Event): Serializable {
        return ""
    }
}