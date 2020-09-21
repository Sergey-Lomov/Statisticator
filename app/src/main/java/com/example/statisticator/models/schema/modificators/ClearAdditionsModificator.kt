package com.example.statisticator.models.schema.modificators

import com.example.statisticator.models.Event
import com.example.statisticator.models.LoggingState

class ClearAdditionsModificator: SessionStateModificator {

    override fun modify(state: LoggingState, event: Event) {
        state.additions.clear()
    }
}