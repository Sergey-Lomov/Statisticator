package com.example.statisticator.service

import com.example.statisticator.models.AttributesContainer
import java.io.Serializable

class LoggingState: ProcessingState {

    override val variables: MutableMap<String, Serializable>
        get() = variablesContainer.content
    private val variablesContainer = AttributesContainer()

    val additions: MutableMap<String, Serializable>
        get() = additionsContainer.content
    private val additionsContainer = AttributesContainer()
}