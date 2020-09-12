package com.example.statisticator.service

import android.content.Context
import com.example.statisticator.models.Event
import com.example.statisticator.models.SessionState
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileReader


class DataStoreManager {

    private val LOG_FOLDER = "log"
    private val STATE_FILENAME = "state.json"
    private val EVENT_EXTENSION = ".json"
    private val EVENT_NAME_SEPARATOR = "_"

    private val context: Context
    private val gson = GsonBuilder()
        .registerTypeAdapter(SessionState::class.java, SessionStateAdapter())
        .create()

    constructor (context: Context) {
        this.context = context
    }

    fun saveEvent(event: Event) {
        val fileName = event.timestamp + EVENT_NAME_SEPARATOR + event.model.type + EVENT_NAME_SEPARATOR + event.id.hashCode() + EVENT_EXTENSION
        val logDir = File(context.filesDir.absolutePath + "/" + LOG_FOLDER + "/")
        logDir.mkdirs()
        val file = File(logDir, fileName)
        val json = Gson().toJson(event.attributes)
        file.writeText(json)
    }

    fun saveSessionState(state: SessionState) {
        val file = File(context.filesDir.absolutePath, STATE_FILENAME)
        val json = gson.toJson(state)
        file.writeText(json)
    }

    fun loadSessionState(): SessionState {
        val file = File(context.filesDir.absolutePath, STATE_FILENAME)
        if (!file.exists())
            return SessionState()
        return gson.fromJson(FileReader(file), SessionState::class.java)
    }
}