package com.example.statisticator.models.schema.modificators

import com.example.statisticator.models.Event
import com.example.statisticator.models.LoggingState

data class RemoveAdditionModificator (
    val attribute: String
): SessionStateModificator {

    override fun modify(state: LoggingState, event: Event) {
        state.additions.remove(attribute)
    }
}