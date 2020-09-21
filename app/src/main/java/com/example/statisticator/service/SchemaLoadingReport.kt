package com.example.statisticator.service

import com.example.statisticator.models.schema.MenuItemModel
import com.example.statisticator.service.factories.TypedEntitiesOwner

sealed class SchemaLoadingIssue
data class ItemWithoutTarget(val item: MenuItemModel) : SchemaLoadingIssue()
data class EntityWithoutType(val owner: TypedEntitiesOwner, val json: String) : SchemaLoadingIssue()
data class UnsupportedEntityType(val owner: TypedEntitiesOwner, val json: String) : SchemaLoadingIssue()
data class InvalidEntityStructure(val owner: TypedEntitiesOwner,
                                  val message: String?,
                                  val json: String) : SchemaLoadingIssue()
data class InvalidModificatorStructure(val owner: TypedEntitiesOwner) : SchemaLoadingIssue()

data class SchemaLoadingReport (
    val issues:ArrayList<SchemaLoadingIssue> = ArrayList()
)