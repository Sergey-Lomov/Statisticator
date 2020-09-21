package com.example.statisticator.models.schema

import com.example.statisticator.models.schema.attributes.Attribute
import com.example.statisticator.models.schema.modificators.SessionStateModificator
import com.example.statisticator.service.factories.TypedEntitiesOwner

data class EventModel (
    override val id: String,
    val type: String,
    val title: String? = null,
    val attributes: ArrayList<Attribute> = ArrayList(),
    val modificators: ArrayList<SessionStateModificator> = ArrayList()
): ItemTarget, TypedEntitiesOwner {
    override val targetType: ItemTargetType
        get() = ItemTargetType.Event
}
