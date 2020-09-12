package com.example.statisticator.models.schema

import com.example.statisticator.models.schema.attributes.EventAttribute
import com.example.statisticator.models.schema.modificators.SessionStateModificator

data class EventModel (
    val id: String,
    val type: String,
    val title: String? = null,
    val attributes: ArrayList<EventAttribute> = ArrayList(),
    val modificators: ArrayList<SessionStateModificator> = ArrayList()
): ItemTarget {
    override val targetType: ItemTargetType
        get() = ItemTargetType.Event
}
