package com.example.statisticator.models.schema.attributes

data class DynamicArrayAttribute (
    override val id: String,
    override val title: String?,
    val sizeVar: String,
    override val prototype: EditableAttribute
): ArrayAttribute(id, title, prototype)