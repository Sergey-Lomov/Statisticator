package com.example.statisticator.service.adapters

import com.example.statisticator.models.AttributesContainer
import com.google.gson.*
import java.io.Serializable
import java.lang.reflect.Type

class AttributesContainerAdapter: JsonSerializer<AttributesContainer>, JsonDeserializer<AttributesContainer> {

    private val ATTRIBUTE = "attribute"
    private val CLASSNAME = "classname"
    private val DATA = "data"

    override fun serialize(
        src: AttributesContainer?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val array = JsonArray()

        src?.content?.forEach() {
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
    ): AttributesContainer {
        val container = AttributesContainer()
        val map: MutableMap<String, Serializable> = mutableMapOf()
        if (context == null) return container
        val jsonElement = json ?: return container
        val jsonArray = if (jsonElement.isJsonArray) jsonElement.asJsonArray else return container

        jsonArray.forEach each@{
            val jsonObject = if (it.isJsonObject) it.asJsonObject else return@each
            val attribute = jsonObject.get(ATTRIBUTE).asString
            val className = jsonObject.get(CLASSNAME).asString
            val valueClass = Class.forName(className)
            val value = Gson().fromJson(jsonObject.get(DATA), valueClass)
            map[attribute] = value as? Serializable ?: return@each
        }

        container.content.putAll(map)
        return container
    }
}