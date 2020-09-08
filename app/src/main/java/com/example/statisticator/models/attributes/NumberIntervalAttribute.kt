package com.example.statisticator.models.attributes

data class NumberIntervalAttribute (
    override val id: String,
    override val title: String,
    val minValue: Int,
    val maxValue: Int
): EventAttributeModel