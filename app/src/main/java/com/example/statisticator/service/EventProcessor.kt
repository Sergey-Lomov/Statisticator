package com.example.statisticator.service

import android.content.Context
import com.example.statisticator.models.Event
import com.example.statisticator.models.SessionState
import com.example.statisticator.models.schema.attributes.CalculatableAttribute

class EventProcessor {

    fun saveEvent(event: Event, state: SessionState, context: Context) {
        val calculatable = event.model.attributes.filterIsInstance<CalculatableAttribute>()
        calculatable.forEach each@{
            val value = it.calculateFor(event, state) ?: return@each
            event.attributes[it.id] = value
        }
        event.attributes.putAll(state.additions)

        event.model.modificators.forEach {
            it.modify(state, event)
        }

        val dataStoreManager = DataStoreManager(context)
        dataStoreManager.saveEvent(event)
        if (event.model.modificators.isNotEmpty())
            dataStoreManager.saveSessionState(state)

    }
}