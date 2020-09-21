package com.example.statisticator.models.schema.modificators

import com.example.statisticator.models.Event
import com.example.statisticator.service.LoggingState

data class SetVarModificator (
    val variable: String,
    val valueKey: String
): SessionStateModificator {

    override fun modify(state: LoggingState, event: Event) {
        val value = event.attributes[valueKey] ?: return
        state.variables[variable] = value
    }
}