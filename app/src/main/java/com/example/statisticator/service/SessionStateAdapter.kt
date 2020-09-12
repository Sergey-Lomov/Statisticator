package com.example.statisticator.service

import com.example.statisticator.models.SessionState
import com.google.gson.*
import java.io.Serializable
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf

class SessionStateAdapter: JsonSerializer<SessionState>, JsonDeserializer<SessionState> {

    private val ADDITIONS = "additions"
    private val VARIABLES = "variables"
    private val ATTRIBUTE = "attribute"
    private val CLASSNAME = "classname"
    private val DATA = "data"

    override fun serialize(
        src: SessionState?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonObject = JsonObject()
        val state = src ?: return jsonObject

        val additionsObject = serializeMap(state.additions, context)
        jsonObject.add(ADDITIONS, additionsObject);
        val variablesObject = serializeMap(state.variables, context)
        jsonObject.add(VARIABLES, variablesObject);

        return jsonObject;
    }

    private fun serializeMap(map: Map<String, Serializable>,
                             context: JsonSerializationContext?
    ): JsonElement {
        val array = JsonArray()
        map.forEach() {
            val attrObject = JsonObject()
            attrObject.addProperty(ATTRIBUTE, it.key);
            attrObject.addProperty(CLASSNAME, it.value::class.java.name);
            attrObject.add(DATA, context?.serialize(it.value));
            array.add(attrObject)
        }
        return array
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SessionState {
        val state = SessionState()
        val jsonObject = json as? JsonObject ?: return state
        val additions = deserializeMap(jsonObject.get(ADDITIONS), context)
        val variables = deserializeMap(jsonObject.get(VARIABLES), context)

        state.additions.putAll(additions)
        state.variables.putAll(variables)

        return state
    }

    private fun deserializeMap(jsonElement: JsonElement,
                               context: JsonDeserializationContext?
    ): Map<String, Serializable> {
        val map: MutableMap<String, Serializable> = mutableMapOf()
        if (context == null) return map
        val jsonArray = if (jsonElement.isJsonArray) jsonElement.asJsonArray else return map

        jsonArray.forEach each@{
            val jsonObject = if (it.isJsonObject) it.asJsonObject else return@each
            val attribute = jsonObject.get(ATTRIBUTE).asString
            val className = jsonObject.get(CLASSNAME).asString
            val valueClass = Class.forName(className)
            val value = Gson().fromJson(jsonObject.get(DATA), valueClass)
            map[attribute] = value as? Serializable ?: return@each
        }

        return map
    }
}