package com.example.statisticator.models

import java.io.Serializable

class AttributesContainer: Serializable {
    val content: MutableMap<String, Serializable> = mutableMapOf()
}