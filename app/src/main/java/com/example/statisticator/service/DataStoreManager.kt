package com.example.statisticator.service

import android.content.Context
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.AttributesContainer
import com.example.statisticator.models.Event
import com.example.statisticator.models.LoggingState
import com.example.statisticator.models.schema.EventModel
import com.example.statisticator.models.schema.SchemaModel
import com.example.statisticator.service.adapters.AttributesContainerAdapter
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileReader


class DataStoreManager {

    private val LOG_FOLDER = "log"
    private val STATE_FILENAME = "state.json"
    private val EVENT_EXTENSION = ".json"
    private val EVENT_NAME_SEPARATOR = "_"

    private val eventTypeKey = Constants.EventParsingKeys.Type.value

    private val context: Context
    private val gson = GsonBuilder()
        //.registerTypeAdapter(SessionState::class.java, SessionStateAdapter())
        .registerTypeAdapter(AttributesContainer::class.java, AttributesContainerAdapter())
    .create()
    private var logDir: File
    private val stateFile: File


    constructor (context: Context) {
        this.context = context
        logDir = File(context.filesDir.absolutePath + "/" + LOG_FOLDER + "/")
        stateFile = File(context.filesDir.absolutePath, STATE_FILENAME)
    }

    fun saveEvent(event: Event) {
        val fileName = event.timestamp + EVENT_NAME_SEPARATOR + event.model.type + EVENT_NAME_SEPARATOR + event.id.hashCode() + EVENT_EXTENSION
        logDir.mkdirs()
        val file = File(logDir, fileName)
        val json = gson.toJson(event.attributesContainer)
        file.writeText(json)
    }

    fun saveSessionState(state: LoggingState) {
        val json = gson.toJson(state)
        stateFile.writeText(json)
    }

    fun loadSessionState(): LoggingState {
        if (!stateFile.exists())
            return LoggingState()
        return gson.fromJson(FileReader(stateFile), LoggingState::class.java)
    }

    fun loadEventsForSchema(schema: SchemaModel): List<Event> {
        val events: MutableList<Event> = mutableListOf()
        val eventModels = schema.items.mapNotNull { it.target as? EventModel }
        val typeToModel = eventModels.map { it.type to it }.toMap()

        logDir.walk().forEach each@{
            val attributesContainer = gson.fromJson(FileReader(it), AttributesContainer::class.java)
            val type = attributesContainer.values[eventTypeKey] as? String ?: return@each
            val model = typeToModel[type] ?: return@each
            val event = Event(model, attributesContainer)
            events.add(event)
        }

        return events
    }
}