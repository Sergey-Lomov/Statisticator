package com.example.statisticator.models.schema.modificators

import com.example.statisticator.models.Event
import com.example.statisticator.models.SessionState

class ClearVarsModificator: SessionStateModificator {

    override fun modify(state: SessionState, event: Event) {
        state.variables.clear()
    }
}