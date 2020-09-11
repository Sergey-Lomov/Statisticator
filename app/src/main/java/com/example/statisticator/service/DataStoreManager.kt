package com.example.statisticator.service

import android.content.Context
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.Event
import com.google.gson.Gson
import java.io.File
import java.io.Serializable


class DataStoreManager {

    private val LOG_FOLDER = "log"
    private val EVENT_EXTENSION = ".json"
    private val EVENT_NAME_SEPARATOR = "_"

    private val context: Context

    constructor (context: Context) {
        this.context = context
    }

    fun saveEvent(event: Event) {
        val fileName = event.timestamp + EVENT_NAME_SEPARATOR + event.model.type + EVENT_NAME_SEPARATOR + event.id.hashCode() + EVENT_EXTENSION
        val logDir = File(context.filesDir.absolutePath + "/" + LOG_FOLDER + "/")
        logDir.mkdirs()
        val file = File(logDir, fileName)

        val attributes: MutableMap<String, Serializable> = mutableMapOf()
        attributes[Constants.EVENT_ID_KEY] = event.id
        attributes[Constants.EVENT_TYPE_KEY] = event.model.type
        attributes[Constants.EVENT_TIMESTAMP_KEY] = event.timestamp
        attributes.putAll(event.attributes)

        val json = Gson().toJson(attributes)
        file.writeText(json)
    }
}