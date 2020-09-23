package com.example.statisticator.service

import android.content.Context
import com.example.statisticator.models.schema.SchemaModel
import java.io.Serializable
import java.util.*

typealias DataScope = Iterable<Map<String, Serializable>>

class DataScopesManager {
    val scopes = Stack<DataScope>()

    companion object {
        var shared: DataScopesManager = DataScopesManager()
            private set

        fun setupSharedFor(context: Context, schema: SchemaModel) {
            shared = DataScopesManager()
            val events = DataStoreManager(context).loadPlainEventsForSchema(schema)
            val scope = events.map { it.content }
            shared.scopes.add(scope)
        }
    }
}