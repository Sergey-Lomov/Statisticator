package com.example.statisticator.models.schema.modificators

import com.example.statisticator.models.Event
import com.example.statisticator.models.SessionState
import java.io.Serializable

data class SetVarModificator (
    val variable: String,
    val valueKey: String
): SessionStateModificator {

    override fun modify(state: SessionState, event: Event) {
        val value = event.attributes[valueKey] ?: return
        state.variables[variable] = value
    }
}