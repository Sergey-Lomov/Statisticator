package com.example.statisticator.models.schema.modificators

import com.example.statisticator.models.Event
import com.example.statisticator.models.LoggingState
import java.io.Serializable

interface SessionStateModificator: Serializable {

    fun modify(state: LoggingState, event: Event)
}