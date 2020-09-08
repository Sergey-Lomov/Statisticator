package com.example.statisticator.models

import com.example.statisticator.models.attributes.EventAttributeModel

data class EventModel (
    val id: String,
    val title: String? = null,
    val attributes: ArrayList<EventAttributeModel> = ArrayList()
): ItemTarget {
    override val targetType: ItemTargetType
        get() = ItemTargetType.Event
}
