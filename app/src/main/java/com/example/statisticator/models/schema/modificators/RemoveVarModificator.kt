package com.example.statisticator.models.schema.modificators

import com.example.statisticator.models.Event
import com.example.statisticator.models.SessionState

data class RemoveVarModificator (
    val variable: String
): SessionStateModificator {

    override fun modify(state: SessionState, event: Event) {
        state.variables.remove(variable)
    }
}