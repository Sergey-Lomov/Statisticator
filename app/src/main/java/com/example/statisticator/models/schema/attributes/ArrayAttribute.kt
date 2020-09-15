package com.example.statisticator.models.schema.attributes

data class ArrayAttribute (
    override val id: String,
    override val title: String,
    val size: Int,
    val prototype: EditableAttribute
): EditableAttribute