package com.example.statisticator.service

import java.io.Serializable

interface ProcessingState: Serializable {
    val variables: MutableMap<String, Serializable>
}