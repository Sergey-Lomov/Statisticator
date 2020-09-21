package com.example.statisticator.service

import com.example.statisticator.models.AttributesContainer
import java.io.Serializable

class LoggingState: ProcessingState {

    override val variables: MutableMap<String, Serializable>
        get() = variablesContainer.values
    private val variablesContainer = AttributesContainer()

    val additions: MutableMap<String, Serializable>
        get() = additionsContainer.values
    private val additionsContainer = AttributesContainer()
}