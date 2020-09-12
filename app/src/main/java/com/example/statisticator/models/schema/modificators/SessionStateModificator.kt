package com.example.statisticator.models.schema.modificators

import com.example.statisticator.models.Event
import com.example.statisticator.models.SessionState
import java.io.Serializable

interface SessionStateModificator: Serializable {

    fun modify(state: SessionState, event: Event)
}