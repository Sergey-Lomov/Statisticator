package com.example.statisticator.service

import android.content.Context
import com.example.statisticator.models.SchemaPreview
import com.example.statisticator.models.schema.SchemaModel
import java.io.File

class SchemasManager {

    private val context: Context

    private val ICONS_FOLDER = "images"

    constructor(context: Context) {
        this.context = context
    }

    fun loadSchemasList(): Array<SchemaPreview> {
        return emptyArray()
    }

    fun iconFolder(/*schema: SchemaModel*/): File {
        return File(context.filesDir.absolutePath, ICONS_FOLDER)
    }

    fun loadLastSchema(): SchemaModel {
        val schemaId = context.resources.getIdentifier("rk_schema",
            "raw",
            context.packageName)
        val stream = context.resources.openRawResource(schemaId)
        val json = stream.bufferedReader().use { it.readText() }
        return SchemaLoader().loadFromJson(json).schema
    }
}