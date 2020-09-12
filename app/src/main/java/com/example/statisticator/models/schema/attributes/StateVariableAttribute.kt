package com.example.statisticator.models.schema.attributes

import com.example.statisticator.models.Event
import com.example.statisticator.models.SessionState
import java.io.Serializable

data class StateVariableAttribute (
    override val id: String,
    val varKey: String
): CalculatableAttribute {

    override fun calculateFor(event: Event, state: SessionState): Serializable? {
        return state.variables[varKey]
    }
}