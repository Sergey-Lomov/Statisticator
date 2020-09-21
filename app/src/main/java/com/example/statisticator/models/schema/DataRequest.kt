package com.example.statisticator.models.schema

import java.io.Serializable

data class DataRequest (
    val id: String,
    val title: String,
    val query: Query
): Serializable