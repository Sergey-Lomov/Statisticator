package com.example.statisticator.service

import com.example.statisticator.models.AttributesContainer
import java.io.Serializable

class RequestsState: ProcessingState {
    override val variables: MutableMap<String, Serializable> = mutableMapOf()
}