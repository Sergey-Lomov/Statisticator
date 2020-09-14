package com.example.statisticator.models.schema.attributes

import com.google.gson.JsonObject

data class ColorsListAttribute (
    override val id: String,
    override val title: String,
    val colors: ArrayList<Int>
): EditableAttribute