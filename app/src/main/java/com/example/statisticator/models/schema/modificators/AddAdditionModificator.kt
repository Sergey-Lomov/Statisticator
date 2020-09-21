package com.example.statisticator.models.schema.modificators

import com.example.statisticator.models.Event
import com.example.statisticator.models.LoggingState

data class AddAdditionModificator (
    val attribute: String,
    val valueKey: String
): SessionStateModificator {

    override fun modify(state: LoggingState, event: Event) {
        val value = event.attributes[valueKey] ?: return
        state.additions[attribute] = value
    }
}