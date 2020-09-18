package com.example.statisticator.models

import java.io.Serializable

class AttributesContainer: Serializable {
    val values: MutableMap<String, Serializable> = mutableMapOf()
}