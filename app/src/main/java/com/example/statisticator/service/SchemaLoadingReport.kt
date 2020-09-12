package com.example.statisticator.service

import com.example.statisticator.models.schema.EventModel
import com.example.statisticator.models.schema.MenuItemModel

sealed class SchemaLoadingIssue
data class ItemWithoutTarget(val item: MenuItemModel) : SchemaLoadingIssue()
data class AttributeWithoutId(val event: EventModel) : SchemaLoadingIssue()
data class InvalidAttributeStructure(val event: EventModel, val attributeId: String) : SchemaLoadingIssue()
data class InvalidModificatorStructure(val event: EventModel) : SchemaLoadingIssue()

data class SchemaLoadingReport (
    val issues:ArrayList<SchemaLoadingIssue> = ArrayList()
)