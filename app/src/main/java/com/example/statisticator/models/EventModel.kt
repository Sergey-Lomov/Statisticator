package com.example.statisticator.models

import com.example.statisticator.models.attributes.EventAttribute

data class EventModel (
    val id: String,
    val type: String,
    val title: String? = null,
    val attributes: ArrayList<EventAttribute> = ArrayList()
): ItemTarget {
    override val targetType: ItemTargetType
        get() = ItemTargetType.Event
}
