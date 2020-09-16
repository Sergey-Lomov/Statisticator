package com.example.statisticator.models.schema.attributes

class StaticArrayAttribute (
    override val id: String,
    override val title: String?,
    val size: Int,
    override val prototype: EditableAttribute
): ArrayAttribute(id, title, prototype)