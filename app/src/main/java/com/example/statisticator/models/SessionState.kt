package com.example.statisticator.models

import java.io.Serializable

class SessionState: Serializable {

    val variables: MutableMap<String, Serializable> = mutableMapOf()
    val additions: MutableMap<String, Serializable> = mutableMapOf()
}