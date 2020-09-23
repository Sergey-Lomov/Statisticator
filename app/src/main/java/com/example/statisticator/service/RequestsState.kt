package com.example.statisticator.service

import java.io.Serializable

class RequestsState: ProcessingState {

    override val variables: MutableMap<String, Serializable> = mutableMapOf()
}