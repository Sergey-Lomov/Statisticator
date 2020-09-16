package com.example.statisticator.models.schema.attributes

abstract class ArrayAttribute (
    override val id: String,
    override val title: String?,
    open val prototype: EditableAttribute
): EditableAttribute