package com.example.statisticator.models.schema.modificators

import com.example.statisticator.models.Event
import com.example.statisticator.models.SessionState

data class AddAdditionModificator (
    val attribute: String,
    val valueKey: String
): SessionStateModificator {

    override fun modify(state: SessionState, event: Event) {
        val value = event.attributes[valueKey] ?: return
        state.additions[attribute] = value
    }
}