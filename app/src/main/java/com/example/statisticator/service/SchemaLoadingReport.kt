package com.example.statisticator.service

import com.example.statisticator.models.EventModel
import com.example.statisticator.models.ItemTarget
import com.example.statisticator.models.MenuItemModel
import com.example.statisticator.models.attributes.EventAttributeModel

sealed class SchemaLoadingIssue
data class ItemWithoutTarget(val item: MenuItemModel) : SchemaLoadingIssue()
data class AttributeWithoutId(val event: EventModel) : SchemaLoadingIssue()
data class InvalidAttributeStructure(val event: EventModel, val attributeId: String) : SchemaLoadingIssue()

data class SchemaLoadingReport (
    val issues:ArrayList<SchemaLoadingIssue> = ArrayList()
)