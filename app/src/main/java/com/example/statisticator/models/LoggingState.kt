package com.example.statisticator.models

import java.io.Serializable

class LoggingState: Serializable {

    val variables: MutableMap<String, Serializable>
        get() = variablesContainer.values
    private val variablesContainer = AttributesContainer()

    val additions: MutableMap<String, Serializable>
        get() = additionsContainer.values
    private val additionsContainer = AttributesContainer()
}